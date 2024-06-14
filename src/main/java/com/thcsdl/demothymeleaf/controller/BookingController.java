package com.thcsdl.demothymeleaf.controller;

import com.thcsdl.demothymeleaf.dto.request.BookingCreateRequest;
import com.thcsdl.demothymeleaf.dto.request.BookingSearchRequest;
import com.thcsdl.demothymeleaf.dto.request.BookingUpdateRequest;
import com.thcsdl.demothymeleaf.dto.request.RoomCreateRequest;
import com.thcsdl.demothymeleaf.entity.Booking;
import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.entity.Room;
import com.thcsdl.demothymeleaf.repository.BookingRepository;
import com.thcsdl.demothymeleaf.repository.MemberRepository;
import com.thcsdl.demothymeleaf.repository.RoomRepository;
import com.thcsdl.demothymeleaf.service.BookingService;
import com.thcsdl.demothymeleaf.service.RoomService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@RequestMapping("/admin/bookings")
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {
    BookingService bookingService;

    BookingRepository bookingRepository;
    RoomRepository roomRepository;
    MemberRepository memberRepository;

    @GetMapping
    public String bookings(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if ( member == null ) {
            return "redirect:/loginOrRegister";
        }
        if ( !member.getRole().equals("ADMIN"))
            return "redirect:/";
        List<Booking> bookings = bookingRepository.findAll();
        model.addAttribute("bookings", bookings);
        model.addAttribute("bookingSearch", new BookingSearchRequest());
        return "bookings";
    }

    @GetMapping("/create")
    public String createBookingPage(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if ( member == null ) {
            return "redirect:/loginOrRegister";
        }
        if ( !member.getRole().equals("ADMIN"))
            return "redirect:/";


        model.addAttribute("bookingCreate", new BookingCreateRequest());
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("members",memberRepository.findAll());
        return "createBooking";
    }

    @PostMapping("/create")
    public String createBooking(@ModelAttribute(name = "bookingCreate") BookingCreateRequest request, Model model, HttpSession session) {
        model.addAttribute("bookingCreate", request);
        session.setAttribute("bookingCreate", request);

        List<Booking> bookings = bookingService.findBookingByRoomIdAndBookedDate(request.getRoomId(),request.getBookedDate());
        model.addAttribute("notAvailableTime", bookings);

        return "createBooking2";
    }

    @PostMapping("/create2")
    public String createBooking2(@ModelAttribute(name = "bookingCreate") BookingCreateRequest request, Model model, HttpSession session) {
        BookingCreateRequest request2 = (BookingCreateRequest) session.getAttribute("bookingCreate");
        Booking booking = new Booking();
        booking.setRoomid(request2.getRoomId());
        booking.setBookedDate(request2.getBookedDate());
        booking.setBookedTime(request.getBookedTime());
        booking.setExpiredTime(request.getExpiredTime());
        booking.setMemberid(request2.getMemberId());
        booking.setDatetimeOfBooking((new Date()).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
        booking.setPaymentStatus("Unpaid");

        Member member = memberRepository.findById(request2.getMemberId().toString2()).orElseThrow(RuntimeException::new);
        Double paymentDue = ((booking.getRoomid().getPrice()/60) * Duration.between(booking.getBookedTime(), booking.getExpiredTime()).toMinutes());

        if (member.getBookingList() == null){
            paymentDue = paymentDue*0.8;
        }
        if (member.getRank() == null)
        {}
        else if (member.getRank().equals("Silver")) {
            paymentDue = paymentDue*0.95;
        }
        else if (member.getRank().equals("Gold")) {
            paymentDue = paymentDue*0.92;
        }
        else if (member.getRank().equals("Diamond")) {
            paymentDue = paymentDue*0.88;
        }
        Long minutes = Duration.between(booking.getBookedTime(),booking.getExpiredTime()).toMinutes();
        if (minutes < 240) {}
        else if (minutes < 360){
            paymentDue = paymentDue*0.93;
        }
        else {
            paymentDue = paymentDue*0.9;
        }
        booking.setPaymentDue(paymentDue);
        member.setPaymentDue(member.getPaymentDue()+paymentDue);
        memberRepository.save(member);
        bookingRepository.save(booking);
        return "redirect:/admin/bookings";
    }


    @GetMapping("/search")
    public String searchBookings(@ModelAttribute(name = "bookingSearch") BookingSearchRequest request, Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if ( member == null ) {
            return "redirect:/loginOrRegister";
        }
        if ( !member.getRole().equals("ADMIN"))
            return "redirect:/";


        model.addAttribute("bookings", bookingService.findBookingByRoomIdOrMemberIdOrBookedDateOrDateTimeOfBookingOrPaymentStatus(request.getRoomId(),request.getMemberId(),request.getBookedDate(),request.getDateTimeOfBooking(),request.getPaymentStatus()));
         return "bookings";
    }

    @PostMapping("/update")
    public String updateBooking(@RequestParam(name = "id") Integer id, Model model, HttpSession session) {
        Booking booking = bookingRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("bookingUpdate", booking);
        session.setAttribute("bookingUpdate", booking);
        session.setAttribute("oldBooking", booking);
        return "updateBooking";
    }

    @PostMapping("/realupdate")
    public String realUpdateBooking(@ModelAttribute(name = "bookingUpdate") Booking booking, HttpSession session) {
        Booking booking1 = (Booking) session.getAttribute("bookingUpdate");
        Member member = memberRepository.findById(booking1.getMemberid().toString2()).orElseThrow(RuntimeException::new);
        if ( booking1.getPaymentStatus().equals("Unpaid") && booking.getPaymentStatus().equals("Paid") ) {
            member.setPaymentDue(member.getPaymentDue()-booking1.getPaymentDue());
            member.setTotalPaid(member.getTotalPaid()+booking1.getPaymentDue());
            if ( member.getTotalPaid() > 1500 && member.getTotalPaid() <= 3000 && (member.getRank() == null || !member.getRank().equals("Silver"))) {
                member.setRank("Silver");
            }
            else if ( member.getTotalPaid() > 3000 && member.getTotalPaid() <= 5000 && (member.getRank() == null || !member.getRank().equals("Gold"))) {
                member.setRank("Gold");
                member.setPenaltyExemption(4);
            }
            else if ( member.getTotalPaid() > 5000 && (member.getRank() == null || !member.getRank().equals("Diamond"))) {
                member.setRank("Diamond");
                member.setPenaltyExemption(4);
            }
        }
        else if ( booking1.getPaymentStatus().equals("Unpaid") && booking.getPaymentStatus().equals("Cancelled") ) {
            member.setPaymentDue(member.getPaymentDue()-booking1.getPaymentDue());
            member.setPenaltyExemption(member.getPenaltyExemption()-1);
            if ( member.getPenaltyExemption() == 0 ) {
                if ( member.getRank() == null || !member.getRank().equals("Silver")) {
                    member.setPenaltyExemption(3);
                }
                else if (member.getRank().equals("Gold") || member.getRank().equals("Diamond")) {
                    member.setPenaltyExemption(4);
                }
                member.setPaymentDue(member.getPaymentDue()-200);
            }
        }
        booking1.setPaymentStatus(booking.getPaymentStatus());
        bookingRepository.save(booking1);
        memberRepository.save(member);
        return "redirect:/admin/bookings";
    }
}

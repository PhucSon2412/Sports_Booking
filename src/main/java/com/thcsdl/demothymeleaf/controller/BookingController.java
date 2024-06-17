package com.thcsdl.demothymeleaf.controller;

import com.thcsdl.demothymeleaf.dto.request.BookingCreateRequest;
import com.thcsdl.demothymeleaf.dto.request.BookingSearchRequest;
import com.thcsdl.demothymeleaf.entity.Booking;
import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.repository.BookingRepository;
import com.thcsdl.demothymeleaf.repository.MemberRepository;
import com.thcsdl.demothymeleaf.service.BookingService;
import com.thcsdl.demothymeleaf.service.MemberService;
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
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RequestMapping("/admin/bookings")
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {
    BookingService bookingService;

    BookingRepository bookingRepository;
    MemberRepository memberRepository;
    private final MemberService memberService;
    private final RoomService roomService;

    @GetMapping
    public String bookings(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if ( member == null ) {
            return "redirect:/loginOrRegister";
        }
        if ( !member.getRole().equals("ADMIN"))
            return "redirect:/";
        List<Booking> bookings = bookingService.findAllBooking();
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
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("members",memberService.getAllMembers());
        model.addAttribute("createFail",0);
        return "createBooking";
    }

    @PostMapping("/create")
    public String createBooking(@ModelAttribute(name = "bookingCreate") BookingCreateRequest request, Model model, HttpSession session) {
        model.addAttribute("bookingCreate", request);
        session.setAttribute("bookingCreate", request);
        model.addAttribute("createFail",0);
        if (request.getBookedDate().isBefore(LocalDate.now())){
            model.addAttribute("bookingCreate", new BookingCreateRequest());
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("members",memberService.getAllMembers());
            model.addAttribute("createFail",1);
            return "createBooking";
        }
        if (request.getMemberId().getRank() == null && bookingRepository.findBookingsByMemberid(request.getMemberId()).stream().filter(booking -> booking.getPaymentStatus().equals("Unpaid")).toList().size() <= 5){}
        else if (request.getMemberId().getRank() == null && bookingRepository.findBookingsByMemberid(request.getMemberId()).stream().filter(booking -> booking.getPaymentStatus().equals("Unpaid")).toList().size() > 5){
            model.addAttribute("bookingCreate", new BookingCreateRequest());
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("members",memberService.getAllMembers());
            model.addAttribute("createFail",2);
            return "createBooking";
        }
        else if ( request.getMemberId().getRank().equals("Silver") && bookingRepository.findBookingsByMemberid(request.getMemberId()).stream().filter(booking -> booking.getPaymentStatus().equals("Unpaid")).toList().size() > 10) {
            model.addAttribute("bookingCreate", new BookingCreateRequest());
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("members",memberService.getAllMembers());
            model.addAttribute("createFail",2);
            return "createBooking";
        }
        else if ( request.getMemberId().getRank().equals("Gold") && bookingRepository.findBookingsByMemberid(request.getMemberId()).stream().filter(booking -> booking.getPaymentStatus().equals("Unpaid")).toList().size() > 20) {
            model.addAttribute("bookingCreate", new BookingCreateRequest());
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("members",memberService.getAllMembers());
            model.addAttribute("createFail",2);
            return "createBooking";
        }


        List<Booking> bookings = bookingService.findBookingByRoomIdAndBookedDate(request.getRoomId(),request.getBookedDate());
        model.addAttribute("notAvailableTime", bookings);

        return "createBooking2";
    }

    @PostMapping("/create2")
    public String createBooking2(@ModelAttribute(name = "bookingCreate") BookingCreateRequest request, HttpSession session, Model model) {
        BookingCreateRequest request2 = (BookingCreateRequest) session.getAttribute("bookingCreate");
        Booking booking = new Booking();
        List<Booking> bookings = bookingService.findBookingByRoomIdAndBookedDate(request2.getRoomId(),request2.getBookedDate());
        model.addAttribute("notAvailableTime", bookings);
        if (request2.getBookedDate().equals(LocalDate.now())){
            if (request.getBookedTime().isBefore(LocalTime.now()) || request.getExpiredTime().isBefore(LocalTime.now())){
                model.addAttribute("createFail",1);
                model.addAttribute("bookingCreate", request);
                return "createBooking2";
            }
        }
        if (request.getBookedTime().isAfter(request.getExpiredTime())) {
            model.addAttribute("createFail",1);
            model.addAttribute("bookingCreate", request);
            return "createBooking2";
        }
        if (Duration.between(request.getBookedTime(), request.getExpiredTime()).toMinutes() < 60) {
            model.addAttribute("createFail", 2);
            model.addAttribute("bookingCreate", request);
            return "createBooking2";
        }

        for (Booking booking1 : bookings) {
            if (request.getBookedTime().isAfter(booking1.getBookedTime())&&request.getBookedTime().isBefore(booking1.getExpiredTime())){
                model.addAttribute("createFail",1);
                model.addAttribute("bookingCreate", request);
                return "createBooking2";
            }
            else if (request.getExpiredTime().isAfter(booking1.getBookedTime())&&request.getExpiredTime().isBefore(booking1.getExpiredTime())){
                model.addAttribute("createFail",1);
                model.addAttribute("bookingCreate", request);
                return "createBooking2";
            }
            else if (request.getBookedTime().isBefore(booking1.getBookedTime())&&request.getExpiredTime().isAfter(booking1.getExpiredTime())){
                model.addAttribute("createFail",1);
                model.addAttribute("bookingCreate", request);
                return "createBooking2";
            }
            else if (request.getBookedTime().equals(booking1.getBookedTime())||request.getBookedTime().equals(booking1.getExpiredTime())){
                model.addAttribute("createFail",1);
                model.addAttribute("bookingCreate", request);
                return "createBooking2";
            }
            else if (request.getExpiredTime().equals(booking1.getExpiredTime())||request.getExpiredTime().equals(booking1.getBookedTime())){
                model.addAttribute("createFail",1);
                model.addAttribute("bookingCreate", request);
                return "createBooking2";
            }
        }


        booking.setRoomid(request2.getRoomId());
        booking.setBookedDate(request2.getBookedDate());
        booking.setBookedTime(request.getBookedTime());
        booking.setExpiredTime(request.getExpiredTime());
        booking.setMemberid(request2.getMemberId());
        booking.setDatetimeOfBooking((new Date()).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
        booking.setPaymentStatus("Unpaid");

        Member member = memberService.getMemberById(request2.getMemberId().toString2());
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


        model.addAttribute("bookings", bookingService.findBookingByMemberIdOrDateTimeOfBookingOrPaymentStatus(request.getMemberId(),request.getDateTimeOfBooking(),request.getPaymentStatus()));
//        model.addAttribute("bookings", bookingRepository.findBooking(request.getMemberId().getId(),request.getDateTimeOfBooking(),request.getPaymentStatus()));
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
                Booking booking2 = new Booking();
                booking2.setMemberid(member);
                booking2.setDatetimeOfBooking(LocalDate.now());
                booking2.setPaymentStatus("Unpaid");
                booking2.setPaymentDue(200d);
                member.setPaymentDue(member.getPaymentDue()+booking2.getPaymentDue());
                bookingRepository.save(booking2);
            }
        }
        booking1.setPaymentStatus(booking.getPaymentStatus());
        bookingRepository.save(booking1);
        memberRepository.save(member);
        return "redirect:/admin/bookings";
    }
}

package com.thcsdl.demothymeleaf.controller;

import com.thcsdl.demothymeleaf.dto.request.MemberLoginRequest;
import com.thcsdl.demothymeleaf.dto.request.MemberRegisterRequest;
import com.thcsdl.demothymeleaf.entity.Booking;
import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.repository.BookingRepository;
import com.thcsdl.demothymeleaf.repository.MemberRepository;
import com.thcsdl.demothymeleaf.repository.RoomRepository;
import com.thcsdl.demothymeleaf.service.LoginService;
import com.thcsdl.demothymeleaf.service.MemberService;
import com.thcsdl.demothymeleaf.service.RoomService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {
    LoginService loginService;
    MemberService memberService;
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @GetMapping
    public String showHomePage(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if ( member != null ) {
            model.addAttribute("member", member);
        }
        return "index";
    }

    @GetMapping("/loginOrRegister")
    public String register(Model model, HttpSession session) {
        model.addAttribute("registerRequest", new MemberRegisterRequest("","",""));
        model.addAttribute("loginRequest", new MemberLoginRequest("",""));
        model.addAttribute("loginFail", false);
        model.addAttribute("registerFail", false);
        Member member = (Member) session.getAttribute("member");
        if ( member != null ) {
            if ( member.getRole().equals("ADMIN") ) {
                return "indexAdmin";
            } else
                return "redirect:/";
        }
        return "regist";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerRequest") MemberRegisterRequest request,HttpSession session, Model model){
        boolean registerFail = false;
        if ( memberService.getMemberByEmail(request.getEmail()) != null ) {
            registerFail = true;
            model.addAttribute("registerFail", registerFail);
            model.addAttribute("loginFail", false);
            model.addAttribute("registerRequest", new MemberRegisterRequest("","",""));
            model.addAttribute("loginRequest", new MemberLoginRequest("",""));
            return "regist";
        }
        session.setAttribute("member",memberService.createMember(request));
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") MemberLoginRequest request,HttpSession session, Model model){
        Member member = loginService.login(request);
        boolean loginFail = false;
        if ( member == null ) {
            loginFail = true;
            model.addAttribute("registerFail", false);
            model.addAttribute("loginFail", loginFail);
            model.addAttribute("registerRequest", new MemberRegisterRequest("","",""));
            model.addAttribute("loginRequest", new MemberLoginRequest("",""));
            return "regist";
        }
        else model.addAttribute("loginFail", loginFail);
        session.setAttribute("member",member);
        if ( member.getRole().equals("ADMIN") ) {
            return "redirect:/admin";
        } else
            return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("member",new Member());
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session, Model model) {

        Member member = (Member) session.getAttribute("member");
        if ( member == null ) {
            return "redirect:/loginOrRegister";
        }
        if ( member.getRole().equals("ADMIN") ) {
            model.addAttribute("memberCount", memberRepository.count());
            model.addAttribute("roomCount", roomRepository.count());
            model.addAttribute("bookingCount", bookingRepository.count());
            return "redirect:/admin/members";
        }
        return "redirect:/";
    }

    @GetMapping("/myInfo")
    public String myInfo(HttpSession session, Model model) {
        if (session.getAttribute("updateFail") != null) {
            if (session.getAttribute("updateFail").equals("Fail")){
                model.addAttribute("updateFail", 1);
            }
            else model.addAttribute("updateFail", 0);
        }
        session.setAttribute("updateFail","NoFail");
        Member member2 = (Member) session.getAttribute("member");
        if ( member2 == null ) {
            return "redirect:/loginOrRegister";
        }
        Member member1 = memberRepository.findByEmail(member2.getEmail());
        session.setAttribute("member",member1);
        Member member = (Member) session.getAttribute("member");

        model.addAttribute("member", member);
        model.addAttribute("updateMember", new Member());
//        List<Booking> notpaid = bookingRepository.findAllUnpaidbyMemberId(member.getId());
        List<Booking> notpaid = bookingRepository.findAll().stream().filter(booking -> booking.getMemberid().toString2().equals(member.toString2())).toList().stream().filter(booking -> booking.getPaymentStatus().equals("Unpaid")).toList();
        model.addAttribute("notpaid", notpaid);
        List<String> roomType = new ArrayList<>();
        Integer i = 0;
        for (Booking booking : notpaid) {
            if (booking.getRoomid() == null) {
                roomType.add("Tiền phạt");
            }
            else roomType.add(i,(roomRepository.findById(booking.getRoomid().toString2()).orElseThrow(RuntimeException::new)).getRoomType());
            i++;
        }
        model.addAttribute("roomType", roomType);
//        List<Booking> bookings = bookingRepository.findAllBookingbyMemberId(member.getId());
        List<Booking> bookings = bookingRepository.findAll().stream().filter(booking -> booking.getMemberid().toString2().equals(member.toString2())).toList();
        model.addAttribute("bookings", bookings);

//        Double paymentDue = member.getPaymentDue();
//        for (Booking booking : notpaid) {
//            paymentDue = paymentDue - booking.getPaymentDue();
//        }
//        model.addAttribute("paymentDue", paymentDue);


        List<String> roomType1 = new ArrayList<>();
        i = 0;
        for (Booking booking : bookings) {
            if (booking.getRoomid() == null){
                roomType1.add("Tiền phạt");
            }
            else roomType1.add(i,(roomRepository.findById(booking.getRoomid().toString2()).orElseThrow(RuntimeException::new)).getRoomType());
            i++;
        }
        model.addAttribute("roomTypeAll", roomType1);

        List<Boolean> cancelable = new ArrayList<>();
        i = 0;
        for (Booking booking : notpaid) {
            LocalDate bookedDate = booking.getBookedDate();
            LocalDate currentDate = LocalDate.now();
            if (bookedDate != null) {
                if (member.getRank() == null || member.getRank().equals("Silver") || member.getRank().equals("Gold")) {
                    if ( currentDate.isBefore(bookedDate.minusDays(2)) ) {
                        cancelable.add(true);
                    }
                    else cancelable.add(false);
                }
                else if (member.getRank().equals("Diamond")){
                    if ( currentDate.isBefore(bookedDate.minusDays(1))) {
                        cancelable.add(true);
                    }
                    else cancelable.add(false);
                }
            }
            else cancelable.add(false);

        }
        model.addAttribute("cancelable", cancelable);
//        model.addAttribute("paymentDue", paymentDue);

        return "user";
    }
}

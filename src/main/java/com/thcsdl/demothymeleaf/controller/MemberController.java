package com.thcsdl.demothymeleaf.controller;

import com.thcsdl.demothymeleaf.dto.request.MemberUpdateRequest;
import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.repository.MemberRepository;
import com.thcsdl.demothymeleaf.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/members")
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberController {
    MemberService memberService;
    private final MemberRepository memberRepository;
    PasswordEncoder passwordEncoder;

    @GetMapping
    public String getAllMembers(Model model, HttpSession session) {

        Member member = (Member) session.getAttribute("member");
        if ( member == null ) {
            return "redirect:/loginOrRegister";
        }
        if ( !member.getRole().equals("ADMIN"))
            return "redirect:/";

        List<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping("/search")
    public String searchMemberByEmail(@RequestParam(name = "email") String email, Model model, HttpSession session) {
        Member member1 = (Member) session.getAttribute("member");
        if ( member1 == null ) {
            return "redirect:/loginOrRegister";
        }
        if ( !member1.getRole().equals("ADMIN"))
            return "redirect:/";

        Member member = memberService.getMemberByEmail(email);
        if (member == null) {
            model.addAttribute("members", memberService.getAllMembers());
        }
        else model.addAttribute("members", member);

        return "members";
    }

    @PostMapping("/update")
    public String updateMember(@RequestParam(name = "id") String id, Model model, HttpSession session) {
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("updateMember", member);
        session.setAttribute("updateMember", member);
        return "updateMember";
    }

    @PostMapping("/realupdate")
    public String realUpdateMember(@ModelAttribute("updateMember") Member member, HttpSession session) {
        Member member1 = (Member) session.getAttribute("updateMember");
        member1.setEmail(member.getEmail());
        member1.setUsername(member.getUsername());
        member1.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member1);
        return "redirect:/admin/members";
    }


    @PostMapping("/delete")
    public String deleteMember(@RequestParam(name = "id") String id) {
        memberService.deleteMember(id);
        return "redirect:/admin/members";
    }
}

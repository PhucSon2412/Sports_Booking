package com.thcsdl.demothymeleaf.service;


import com.thcsdl.demothymeleaf.dto.request.MemberRegisterRequest;
import com.thcsdl.demothymeleaf.dto.request.MemberUpdateRequest;
import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;



@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberService {
    MemberRepository memberRepository;
    PasswordEncoder passwordEncoder;

    public Member createMember(MemberRegisterRequest registerRequest) {
        if (memberRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException();
        }
        Member member = new Member();
        member.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        member.setEmail(registerRequest.getEmail());
        member.setUsername(registerRequest.getUsername());
        member.setMemberSince(new Date());
        member.setPaymentDue(0.0d);
        member.setRole("USER");
        member.setTotalPaid(0.0d);
        member.setPenaltyExemption(3);
        memberRepository.save(member);
        return member;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member updateMember(MemberUpdateRequest updateRequest) {
        Member member = memberRepository.findById(updateRequest.getId()).orElseThrow(RuntimeException::new);
        member.setEmail(updateRequest.getEmail());
        member.setPassword(updateRequest.getPassword());
        member.setUsername(updateRequest.getUsername());
        return memberRepository.save(member);
    }

    public void deleteMember(String id) {
        memberRepository.deleteById(id);
    }

}

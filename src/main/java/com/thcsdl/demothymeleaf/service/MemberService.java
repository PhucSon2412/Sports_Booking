package com.thcsdl.demothymeleaf.service;


import com.thcsdl.demothymeleaf.dto.request.MemberRegisterRequest;
import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberService {
    MemberRepository memberRepository;
    PasswordEncoder passwordEncoder;

    public Member createMember(MemberRegisterRequest registerRequest) {
        memberRepository.createMember(registerRequest.getUsername(), passwordEncoder.encode(registerRequest.getPassword()), registerRequest.getEmail());
        return memberRepository.findByEmail(registerRequest.getEmail());
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAllMember();
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public void deleteMember(String id) {
        memberRepository.deleteMember(id);
    }

    public void updateMemberUsername(String id, String username) {
        memberRepository.updateMemberUsername(id, username);
    }

    public void updateMemberPassword(String id, String password) {
        memberRepository.updateMemberPassword(id, password);
    }

    public void updateMemberEmail(String id, String email) {
        memberRepository.updateMemberEmail(id, email);
    }

    public Member getMemberById(String id) {
        return memberRepository.findByMemberId(id);
    }

}

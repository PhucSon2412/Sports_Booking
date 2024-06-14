package com.thcsdl.demothymeleaf.service;

import com.thcsdl.demothymeleaf.dto.request.MemberLoginRequest;
import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginService {
    MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member login(MemberLoginRequest loginRequest) {
        Member member =memberRepository.findByEmail(loginRequest.getEmail());
        if (member == null) {
            return null;
        }
        boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), member.getPassword());
        if (authenticated) {
            return member;
        }
        else return null;
    }
}

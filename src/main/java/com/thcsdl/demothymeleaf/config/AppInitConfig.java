package com.thcsdl.demothymeleaf.config;

import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(MemberRepository memberRepository){
        return args -> {
            if (memberRepository.findByEmail("admin@gmail.com") == null ){
                Member member = Member.builder()
                        .username("Admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .memberSince(new Date())
                        .paymentDue(0.0d)
                        .role("ADMIN")
                        .totalPaid(0.0d)
                        .penaltyExemption(0)
                        .build();
                memberRepository.save(member);
            }
        };
    }
}

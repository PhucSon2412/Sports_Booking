package com.thcsdl.demothymeleaf.repository;

import com.thcsdl.demothymeleaf.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
        boolean existsByEmail(String email);
        Member findByEmail(String email);
        List<Member> findByRole(String role);
}

package com.thcsdl.demothymeleaf.repository;

import com.thcsdl.demothymeleaf.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
        @Query("SELECT m FROM Member m WHERE m.email = :email")
        Member findByEmail(@Param("email") String email);

        @Query("SELECT m FROM Member m WHERE m.id = :id")
        Member findByMemberId(@Param("id") String id);

        @Procedure(name = "register")
        Member createMember(@Param("username") String username, @Param("email") String email, @Param("password") String password);

        @Query("SELECT m FROM Member m")
        List<Member> findAllMember();

        @Procedure(name = "UpdateMemberUsername")
        void updateMemberUsername(@Param("id") String id, @Param("username") String username );

        @Procedure(name = "UpdateMemberEmail")
        void updateMemberEmail(@Param("id") String id, @Param("email") String username );

        @Procedure(name = "UpdateMemberPassword")
        void updateMemberPassword(@Param("id") String id, @Param("password") String username );

        @Procedure(name = "DeleteMember")
        void deleteMember(@Param("id") String id);
}

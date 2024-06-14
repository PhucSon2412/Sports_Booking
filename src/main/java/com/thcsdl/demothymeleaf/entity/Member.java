package com.thcsdl.demothymeleaf.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "member_since", nullable = false)
    private Date memberSince;

    @Column(name = "payment_due", nullable = false)
    private Double paymentDue;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "total_paid", nullable = false)
    private Double totalPaid;

    @Column(name = "rank" , nullable = true)
    private String rank;

    @Column(name = "penalty_exemption", nullable = false)
    private Integer penaltyExemption;

    @OneToMany(mappedBy = "memberid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookingList;


    @Override
    public String toString() {
        return "Member [id=" + id + ", email=" + email + "]";
    }

    public String toString2(){
        return id;
    }
}
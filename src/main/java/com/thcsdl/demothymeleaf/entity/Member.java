package com.thcsdl.demothymeleaf.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NamedStoredProcedureQuery(
        name = "register",
        procedureName = "register",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "username", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "email", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "password", type = String.class),
        },
        resultClasses = Member.class
)

@NamedStoredProcedureQuery(
        name = "UpdateMemberUsername",
        procedureName = "UpdateMemberUsername",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "username", type = String.class),
        }
)

@NamedStoredProcedureQuery(
        name = "UpdateMemberEmail",
        procedureName = "UpdateMemberEmail",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "email", type = String.class),
        }
)

@NamedStoredProcedureQuery(
        name = "UpdateMemberPassword",
        procedureName = "UpdateMemberPassword",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "password", type = String.class),
        }
)

@NamedStoredProcedureQuery(
        name = "DeleteMember",
        procedureName = "DeleteMember",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = String.class),
        }
)


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
        return email;
    }

    public String toString2(){
        return id;
    }
}
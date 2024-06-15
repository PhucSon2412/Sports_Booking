package com.thcsdl.demothymeleaf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NamedStoredProcedureQuery(
        name = "AddBooking",
        procedureName = "AddBooking",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "bookedDate", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "bookedTime", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "datetimeOfBooking", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "expiredTime", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "paymentDue", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "paymentStatus", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "memberId", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "roomId", type = String.class),
        }
)


@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room roomid;

    @Column(name = "booked_date", nullable = false)
    private LocalDate bookedDate;

    @Column(name = "booked_time", nullable = false)
    private LocalTime bookedTime;

    @Column(name = "expired_time", nullable = false)
    private LocalTime expiredTime;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member memberid;

    @Column(name = "datetime_of_booking", nullable = false)
    private LocalDate datetimeOfBooking;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "payment_due", nullable = false)
    private Double paymentDue;

    @Override
    public String toString() {
        return "Booking [id=" + id + ", paymentStatus=" + paymentStatus + "]" + "\n";
    }

    public String toString2() {
        return roomid.toString2();
    }
}
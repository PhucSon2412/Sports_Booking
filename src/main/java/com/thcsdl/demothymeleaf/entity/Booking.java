package com.thcsdl.demothymeleaf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NamedStoredProcedureQuery(
        name = "SearchBookings",
        procedureName = "SearchBookings",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "memberId", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "dateTimeOfBooking", type = LocalDate.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "paymentStatus", type = String.class),
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
    @JoinColumn(name = "room_id")
    private Room roomid;

    @Column(name = "booked_date")
    private LocalDate bookedDate;

    @Column(name = "booked_time")
    private LocalTime bookedTime;

    @Column(name = "expired_time")
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

    @Column(name = "rating", nullable = true)
    private Integer rating;

    @Override
    public String toString() {
        return "Booking [id=" + id + ", paymentStatus=" + paymentStatus + "]" + "\n";
    }

}
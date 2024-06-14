package com.thcsdl.demothymeleaf.dto.request;

import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreateRequest {
    private Room roomId;
    private LocalDate bookedDate;
    private LocalTime bookedTime;
    private LocalTime expiredTime;
    private Member memberId;
    private LocalDate dateTimeOfBooking;
    private String paymentStatus;
}

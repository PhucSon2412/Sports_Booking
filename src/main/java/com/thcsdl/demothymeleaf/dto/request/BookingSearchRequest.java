package com.thcsdl.demothymeleaf.dto.request;

import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingSearchRequest {
    private Room roomId;
    private Member memberId;
    private LocalDate bookedDate;
    private LocalDate dateTimeOfBooking;
    private String paymentStatus;
}

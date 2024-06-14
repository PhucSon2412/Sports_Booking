package com.thcsdl.demothymeleaf.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingFindRequest {
    private Integer id;
    private LocalDate bookedDate;
    private LocalDate datetimeOfBooking;
    private String paymentStatus;
}

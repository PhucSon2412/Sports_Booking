package com.thcsdl.demothymeleaf.dto.request;

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
public class UserBookingCreateRequest {
    private String roomId;
//    private String roomType;
    private LocalDate bookedDate;
    private LocalTime bookedTime;
    private LocalTime expiredTime;
}

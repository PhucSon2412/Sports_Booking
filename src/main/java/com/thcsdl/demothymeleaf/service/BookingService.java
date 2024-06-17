package com.thcsdl.demothymeleaf.service;

import com.thcsdl.demothymeleaf.entity.Booking;
import com.thcsdl.demothymeleaf.entity.Member;
import com.thcsdl.demothymeleaf.entity.Room;
import com.thcsdl.demothymeleaf.repository.BookingRepository;
import com.thcsdl.demothymeleaf.repository.RoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingService {
    BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final RoomService roomService;

    public List<Booking> findBookingByRoomIdAndBookedDate(Room room, LocalDate bookedDate) {
        List<Booking> bookings1 = bookingRepository.findAll().stream().filter(booking -> booking.getBookedDate()!=null).toList();
        List<Booking> bookings = bookings1.stream()
                .filter(booking -> booking.getBookedDate().equals(bookedDate))
                .toList();
        List<Booking> filteredBookings = bookings.stream()
                .filter(booking -> booking.getRoomid().toString2().equals(room.getId()))
                .toList();

        return filteredBookings;
    }

    public List<Booking> findBookingByMemberIdOrDateTimeOfBookingOrPaymentStatus(Member member, LocalDate dateTimeOfBooking, String paymentStatus ) {
        List<Booking> bookings = bookingRepository.findAll();
        if (member != null) {
            bookings = bookingRepository.findAll().stream().filter(booking -> booking.getMemberid().toString2().equals(member.getId())).toList();
        }
        List<Booking> bookings1 = bookings;
        if (dateTimeOfBooking != null) {
            bookings1 = bookings.stream().filter(booking -> booking.getDatetimeOfBooking().equals(dateTimeOfBooking)).toList();
        }
        List<Booking> bookings2 = bookings1;
        if (!paymentStatus.equals("UNKNOWN")) {
            bookings2 = bookings1.stream().filter(booking -> booking.getPaymentStatus().equals(paymentStatus)).toList();
        }
        return bookings2;
    }

    public Room findAvailableRoom(String roomType, LocalDate bookedDate, LocalTime bookedTime, LocalTime expiredTime) {
        List<Booking> bookings1 = bookingRepository.findAll().stream().filter(booking -> booking.getBookedDate() != null).toList();
        List<Booking> bookings = bookings1.stream()
                    .filter(booking -> booking.getBookedDate().equals(bookedDate))
                .toList();
        List<Booking> filteredBookings = bookings.stream()
                .filter(booking -> booking.getRoomid().toString3().equals(roomType))
                .toList();
        List<String> roomIds = roomService.getRoomIdByRoomType(roomType);

        if (filteredBookings.size() == 0) {
            return roomRepository.findById(roomIds.getFirst()).orElse(null);
        }

        for (String roomId : roomIds) {
            boolean notBooked = true;
            for (Booking booking : filteredBookings) {
                if (booking.getRoomid().toString2().equals(roomId)) {
                    notBooked = false;
                    if (bookedTime.isAfter(booking.getBookedTime())&&bookedTime.isBefore(booking.getExpiredTime())) {
                    }
                    else if (expiredTime.isBefore(booking.getExpiredTime())&&expiredTime.isAfter(booking.getBookedTime())){
                    }
                    else if (bookedTime.isBefore(booking.getBookedTime())&&expiredTime.isAfter(booking.getExpiredTime())) {
                    }
                    else if (bookedTime.equals(booking.getBookedTime())&&expiredTime.equals(booking.getExpiredTime())) {
                    }
                    else return roomRepository.findById(roomId).orElse(null);
                }
            }
            if (notBooked) {
                return roomRepository.findById(roomId).orElse(null);
            }
        }
        return null;
    }

    public List<Booking> findAllBooking(){
        return bookingRepository.findAllBooking();
    }
}

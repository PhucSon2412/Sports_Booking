package com.thcsdl.demothymeleaf.repository;

import com.thcsdl.demothymeleaf.entity.Booking;
import com.thcsdl.demothymeleaf.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
    @Query("SELECT b FROM Booking b")
    List<Booking> findAllBooking();

    List<Booking> findBookingsByMemberid(Member member);
}

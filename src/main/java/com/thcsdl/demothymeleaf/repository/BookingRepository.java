package com.thcsdl.demothymeleaf.repository;

import com.thcsdl.demothymeleaf.entity.Booking;
import com.thcsdl.demothymeleaf.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
    @Query("SELECT b FROM Booking b")
    List<Booking> findAllBooking();

    @Procedure(name = "SearchBookings")
    @Transactional(readOnly = true)
    List<Booking> findBooking(@Param("memberId") String memberId,@Param("dateTimeOfBooking") LocalDate dateTimeOfBooking, @Param("paymentStatus") String paymentStatus);

    List<Booking> findBookingsByMemberid(Member member);
}

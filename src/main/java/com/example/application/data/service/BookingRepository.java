package com.example.application.data.service;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    //@Query("select b from Booking b WHERE b.company.id = company.id ")

    List<Booking> findByCompany(Company company);

    List<Booking> findByTimeChosen(LocalDateTime timeChosen);

    Booking findByCompanyAndTimeChosen(Company company, LocalDateTime timeChosen);

//    @Query("select b from Booking b " +
//            "where (b.company.id) = (Company.id)")
//    List<Booking> findCompanyBooking(Company company);

   /* @Query("select b.timeChosen from Booking b where (b.company.id) = (Company.id)")
    List<LocalDateTime>bookingTimes(Company company);*/
   @Query("select b from Booking b " +
           "where b.user.id=:userId")
   List<Booking> findBookingByUserId(Integer userId);
}

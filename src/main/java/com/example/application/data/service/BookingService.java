package com.example.application.data.service;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class BookingService extends CrudService<Booking,Integer>{

    private BookingRepository bookingRepository;

    public BookingService(@Autowired BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    protected BookingRepository getRepository() {
        return bookingRepository;
    }
/*    public List<Booking> findAllCompany(Company company) {
        return bookingRepository.findCompanyBooking(company);
    }*/


}

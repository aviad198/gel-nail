package com.example.application.data.service;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.Company;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

public class BookingService extends CrudService<Booking,Integer> {

    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

  /*  public List<Booking> findAllCompany(Company company) {
        return bookingRepository.findCompanyBooking(company);
    }*/

    @Override
    protected BookingRepository getRepository() {
        return bookingRepository;
    }

}

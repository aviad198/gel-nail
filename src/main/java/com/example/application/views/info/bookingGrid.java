package com.example.application.views.info;

import com.example.application.data.entity.*;
import com.example.application.data.service.BookingService;
import com.example.application.data.service.ContactService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "bookings")
public class bookingGrid extends VerticalLayout {

    private BookingService bookingService;

    private Grid<Booking> grid = new Grid<>(Booking.class);

    public bookingGrid(BookingService bookingService) {
        this.bookingService = bookingService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(grid);
        updateList();
    }

    private void updateList() {
        grid.setItems(bookingService.findAll());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.removeColumnByKey("user");
        grid.setColumns("timeChosen");
        grid.addColumn(booking -> {
            Company company = booking.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");
        grid.addColumn(booking -> {
            User user = booking.getUser();
            return user == null ? "-" : user.getUsername();
        }).setHeader("user");

    }
}

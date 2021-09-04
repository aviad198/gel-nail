package com.example.application.views.booking;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.User;
import com.example.application.data.service.BookingService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.Optional;
@PermitAll
@Route(value = "user-booking-view", layout = MainLayout.class)
public class UserBookingView  extends VerticalLayout{

    private Grid<Booking> grid = new Grid<>(Booking.class);
    private BookingService bookingService;
    private AuthenticatedUser authenticatedUser;

    public UserBookingView(AuthenticatedUser authenticatedUser, BookingService bookingService){
        this.authenticatedUser = authenticatedUser;
        this.bookingService =bookingService;
        addClassNames("list-view");
        setSizeFull();
        configureGrid();

        add(grid);
        updateList();
    }

    private void updateList() {
        Optional<User> authUser = authenticatedUser.get();
        if (authUser.isPresent()) {
            grid.setItems(bookingService.findByUser(authUser.get()));
        } else {
            Notification.show("Must signing");
        }

    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.removeColumnByKey("user");
        grid.setColumns("timeChosen");
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");


    }


}

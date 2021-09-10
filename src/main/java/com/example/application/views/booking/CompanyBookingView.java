package com.example.application.views.booking;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.User;
import com.example.application.data.service.BookingService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.example.application.views.nailsalons.NailSalonsView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;
@RolesAllowed("admin")
@Route(value = "company-booking-view", layout = MainLayout.class)
public class CompanyBookingView extends VerticalLayout  implements BeforeEnterObserver {
    private Grid<Booking> grid = new Grid<>(Booking.class);
    private BookingService bookingService;
    private AuthenticatedUser authenticatedUser;
    private Company company;

    public CompanyBookingView(AuthenticatedUser authenticatedUser, BookingService bookingService){
        this.authenticatedUser = authenticatedUser;
        this.bookingService =bookingService;


        addClassNames("list-view");
        setSizeFull();
        configure();
        configureGrid();
        add(createTitle());
        add(grid);
        updateList();
    }
    private void configure(){
        Optional<User> authUser = authenticatedUser.get();
        company = authUser.get().getCompany();
    }
    private void updateList() {
        grid.setItems(bookingService.findByCompany(company));
    }

    private Component createTitle() {
        return new H3("Bookings");
    }
    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.removeColumnByKey("user");
        grid.removeColumnByKey("comment");
        grid.setColumns("timeChosen");
        grid.addColumn(booking -> booking.getUser().getFirstName()).setHeader("First Name").setSortable(true);
        grid.addColumn(booking -> booking.getUser().getLastName()).setHeader("Last Name").setSortable(true);
        grid.addColumn(booking -> booking.getUser().getEmail()).setHeader("Email").setSortable(true);
        grid.addColumn(Booking::getComment).setHeader("comment").setSortable(true);
    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<User> authUser = authenticatedUser.get();
        if (!authUser.isPresent()){
            event.forwardTo(NailSalonsView.class);
        }
    }
}

package com.example.application.views.booking;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.User;
import com.example.application.data.service.BookingService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
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
        add(createTitle());
        add(grid);
        updateList();
    }
    private Component createTitle() {
        return new H3("My Bookings");
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
        grid.addColumn(booking -> {
            return booking.getCompany().getName();
        }).setHeader("Company");


        grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("");

        grid.setSelectionMode(Grid.SelectionMode.NONE);


    }

    private Button createRemoveButton(Grid<Booking> grid, Booking item) {
        @SuppressWarnings("unchecked")
        Button button = new Button("Remove", clickEvent -> {
            ListDataProvider<Booking> dataProvider = (ListDataProvider<Booking>) grid.getDataProvider();
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
            this.bookingService.delete(item.getId());
        });
        return button;
    }
}

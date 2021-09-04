package com.example.application.views.booking;

import com.example.application.data.entity.*;
import com.example.application.data.service.BookingService;
import com.example.application.data.service.CompanyService;
import com.example.application.data.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;

import javax.annotation.security.PermitAll;
import java.time.LocalDateTime;

@PermitAll
public class ScheduleDialog extends Dialog {

    private FormLayout formLayout;
    private BeanValidationBinder<Contact> binder;

    private UserService userService;
    private CompanyService companyService;
    private BookingService bookingService;

    LocalDateTime bookingDayTime;
    private User user;
    Label timeAndDateLabel;


    public ScheduleDialog(UserService userService, CompanyService companyService, BookingService bookingService, Company company) {
        //get service
        formLayout = new FormLayout();


        timeAndDateLabel = new Label("00:00");
        Button bookBtn = new Button("Save");
        Button cancel = new Button("Cancel");

        cancel.addClickListener(e -> close());

        bookBtn.addClickListener(e -> {
            Booking newBooking = new Booking();
            newBooking.setCompany(company);
            newBooking.setUser(user);
            newBooking.setTimeChosen(bookingDayTime);
            bookingService.update(newBooking);
            user.addBooking(newBooking);
            userService.update(user);
            company.addBooking(newBooking);
            companyService.update(company);

            close();
            //TESTING-
            System.out.println("Booking list - ");
            //System.out.println("Company = "+company+"; Company booking = " + company.getBookings().toString());
            System.out.println("User = " + user + "; User booking = " + user.getBookings().toString());


        });
        //choose a treatment
        Select<TypeService> serviceSelect = new Select<>();
        //serviceSelect.setItems()

        //text area for customer comment
        TextArea comment = new TextArea();
        comment.getStyle().set("minHeight", "150px");
        comment.setPlaceholder("Any thing to add..?");

        formLayout.add(timeAndDateLabel, comment, bookBtn, cancel);
        add(formLayout);
    }

    public void setTime(LocalDateTime bookingDayTime) {
        this.bookingDayTime = bookingDayTime;
        timeAndDateLabel.setText(bookingDayTime.toString().replace('T', ' '));
    }

    public void setUser(User user) {
        this.user = user;
    }
}

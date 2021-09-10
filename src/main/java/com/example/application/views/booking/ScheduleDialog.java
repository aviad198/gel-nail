package com.example.application.views.booking;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.User;
import com.example.application.data.service.BookingService;
import com.example.application.data.service.CompanyService;
import com.example.application.data.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;

import javax.annotation.security.PermitAll;
import java.time.LocalDateTime;

@PermitAll
public class ScheduleDialog extends Dialog {

    private FormLayout formLayout;

    private UserService userService;
    private CompanyService companyService;
    private BookingService bookingService;
    private Company company;

    private LocalDateTime bookingDayTime;
    private User user;
    private Label timeAndDateLabel;
    private TextArea comment = new TextArea();

    public ScheduleDialog(UserService userService, CompanyService companyService, BookingService bookingService, Company company, CompanyView companyView) {
        this.userService = userService;
        this.companyService = companyService;
        this.bookingService =bookingService;
        this.company = company;

        //get service
        formLayout = new FormLayout();


        timeAndDateLabel = new Label("00:00");

        Button bookBtn = new Button("Save");
        Button cancel = new Button("Cancel");

        cancel.addClickListener(e -> close());

        bookBtn.addClickListener(e -> book(companyView));

        //text area for customer comment
        comment.getStyle().set("minHeight", "150px");
        comment.setPlaceholder("Any thing to add..?");

        formLayout.add(timeAndDateLabel, comment, bookBtn, cancel);
        add(formLayout);
    }

    private void book(CompanyView companyView) {
        if(bookingService.isBooked(company, bookingDayTime)) {
            Notification.show("This time is no longer available for booking");
        }
        else{
            Booking newBooking = new Booking();
            newBooking.setCompany(company);
            newBooking.setUser(user);
            newBooking.setTimeChosen(bookingDayTime);
            newBooking.setComment(comment.getValue());
            bookingService.update(newBooking);
            user.addBooking(newBooking);
            userService.update(user);
            company.addBooking(newBooking);
            companyService.update(company);
            companyView.refreshSchedule();
        }
        close();
    }

    public void setTime(LocalDateTime bookingDayTime) {
        this.bookingDayTime = bookingDayTime;
        timeAndDateLabel.setText(bookingDayTime.toString().replace('T', ' '));
    }

    public void setUser(User user) {
        this.user = user;
    }
}

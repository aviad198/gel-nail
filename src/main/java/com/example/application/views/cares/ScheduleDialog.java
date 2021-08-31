package com.example.application.views.cares;

import com.example.application.data.entity.*;
import com.example.application.data.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;

import javax.annotation.security.PermitAll;
import java.time.LocalDateTime;

@PermitAll
public class ScheduleDialog extends Dialog{
    private FormLayout formLayout;
    private BeanValidationBinder<Contact> binder;

    LocalDateTime timeChosen;
    private User user;
    Label timeAndDateLabel;


    public ScheduleDialog(Company company) {
        //get service
        formLayout = new FormLayout();


        timeAndDateLabel = new Label("00:00");
        Button bookBtn = new Button("Save");
        Button cancel = new Button("Cancel");

        cancel.addClickListener(e-> close());

        bookBtn.addClickListener(e->{
            Booking newBooking = new Booking(company,user,timeChosen);

           //Contact contact = new Contact();
           //contact.setCompany(company);
           //contact.setEmail(user.getEmail());
           //contact.setFirstName(user.getFirstName());
           //contact.setLastName(user.getLastName());
           user.addBooking(newBooking);
           company.addBooking(newBooking);
           close();
           //TESTING-
           System.out.println("Booking list - ");
           System.out.println("Company = "+company+"; Company booking = " + company.getBookings().toString());
           System.out.println("User = " +user + "; User booking = " + user.getBookings().toString());


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
    public void setTime(LocalDateTime localDateTime){
        timeChosen = localDateTime;
        timeAndDateLabel.setText(timeChosen.toString().replace('T',' '));
    }

    public void setUser(User user) {
        this.user = user;
    }
}

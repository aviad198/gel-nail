package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
public class Booking extends AbstractEntity {

    @ManyToOne
    @NotEmpty(message = "must have a company")
    private Company company;
    @ManyToOne
    @NotEmpty(message = "must have a user")
    private User user;
    @NotEmpty(message = "must have a date and time")
    private LocalDateTime timeChosen;

    public Booking() {

    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTimeChosen() {
        return timeChosen;
    }

    public void setTimeChosen(LocalDateTime timeChosen) {
        this.timeChosen = timeChosen;
    }

    public Booking(Company company, User user, LocalDateTime timeChosen) {
        this.company=company;
        this.user=user;
        this.timeChosen=timeChosen;
    }

}

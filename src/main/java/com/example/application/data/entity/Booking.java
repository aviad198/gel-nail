package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Booking extends AbstractEntity {

    //@NotEmpty(message = "must have a company")
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    //@NotEmpty(message = "must have a user")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //@NotEmpty(message = "must have a date and time")
    private LocalDateTime timeChosen;

    private String comment;


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

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}

package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Contact extends AbstractEntity{

    public enum Status {
        ImportedLead, NotContacted, Contacted, Customer, ClosedLost
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @NotEmpty
    private String firstName = (user == null) ? "" : user.getName();

    @NotNull
    @NotEmpty
    private String lastName = (user == null) ? "" :user.getSurname();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Contact.Status status;

    @Email
    @NotNull
    @NotEmpty
    private String email = (user == null) ? "" :user.getEmail();

    private int rating;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {this.email = email;}

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
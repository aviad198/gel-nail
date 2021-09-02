package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import com.vaadin.flow.component.button.Button;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Entity
public class Company extends AbstractEntity {

    //@NotEmpty(message = "must have a name")
    private String name;
    private String mail;
    private String mainImageURL;
    private int rating;
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(mappedBy = "company")
    private User admin;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Booking> bookings = new LinkedList<>();

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<TypeService> servics = new LinkedList<>();

    public Company() {
        super();
    }

    public Company(String name, User admin, String mainImageURL, int rating, Address address) {
        super();
        this.name=name;
        this.admin =admin;
        this.mainImageURL =mainImageURL;
        this.rating =rating;
        this.address=address;
    }

    public String getMainImageURL() {
        return mainImageURL;
    }

    public void setMainImageURL(String mainImageURL) {
        this.mainImageURL = mainImageURL;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<TypeService> getService() {
        return servics;
    }

    public void setRating(int rating){this.rating = rating;}

    public int getRating(){return rating;}

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

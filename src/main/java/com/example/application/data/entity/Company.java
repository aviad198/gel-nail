package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Company extends AbstractEntity {

    @NotEmpty(message = "Must enter the company name")
    @NotNull(message = "Must enter username")
    private String name = "";
    @Email
    @NotNull
    @NotEmpty
    @Column(unique=true)
    private String mail ;

    private String mainImageURL = "https://picsum.photos/200";
    private int rating = 0 ;

    private String description;

    //to remove
  //  @OneToMany(mappedBy = "company")
   // private List<Contact> employees = new LinkedList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User admin;


    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Booking> bookings = new LinkedList<>();


   // public List<Contact> getEmployees() {return employees;}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMainImageURL() {
        return mainImageURL;
    }

    public void setMainImageURL(String mainImageURL) {
        this.mainImageURL = mainImageURL;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   // public void setEmployees(List<Contact> employees) {this.employees = employees;}

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

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }


    public void addBooking(Booking newBooking) {
        bookings.add(newBooking);
    }
}

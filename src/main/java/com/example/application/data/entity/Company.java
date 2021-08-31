package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import com.vaadin.flow.component.button.Button;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Entity
public class Company extends AbstractEntity {

    private String name;
    private String mail;
    private String mainImageURL;
    private int rating;

    @ManyToOne
    private User admin;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Booking> bookings = new LinkedList<>();

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<TypeService> servics = new LinkedList<>();



    public Company() {
    }
    public Company(String name) {
        String[] ImageLinks = {"https://i.pinimg.com/564x/8d/81/8e/8d818e332b10c594b75b802b79d531ed.jpg", "https://www.ambermarieandcompany.com/copy-of-fun-zone-polka-dot-nail-polish.html?source=googlebase",
                "https://i.pinimg.com/474x/b1/ef/fa/b1effaee264095487da37d45f8b8cd7f.jpg","https://i.pinimg.com/474x/1a/e4/06/1ae406e18c054b1930b2d55f678bab63.jpg",
        "https://i.pinimg.com/474x/c5/79/82/c5798298fc3e9288b705091092dc5075.jpg"};
        setName(name);
        Random rand = new Random();
        setMainImageURL(ImageLinks[rand.nextInt(ImageLinks.length)]);
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
}

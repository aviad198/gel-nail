package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import com.example.application.data.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class User extends AbstractEntity {

    @NotEmpty(message = "Must enter a Username")
    @Length(message = "username length must be at least 4 character and max 10", min = 4, max = 10)
    @NotNull(message = "Must enter username")
    @Column(unique=true)
    private String username;
    @Pattern(message = "Invalid character for name. must be only letters", regexp = "^[A-Za-z]*$")
    @Length(message = "Invalid name length", max = 15)
    @NotEmpty(message = "Must fill first name")
    private String firstName;
    @Pattern(message = "Invalid character for name. must be only letters", regexp = "^[A-Za-z]*$")
    @Length(message = "Invalid name length", max = 15)
    @NotEmpty(message = "Must fill first name")
    private String lastName;
    @JsonIgnore
    @Length(min = 8, max = 64)
    private String hashedPassword;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Lob
    private String profilePictureUrl;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Booking> bookings = new LinkedList<>();

    @Email
    @NotNull
    @NotEmpty
    @Column(unique=true)
    private String email;



    public User() {
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String name) {
        this.firstName = name;
    }
    public String getLastName() {return lastName;}
    public void setLastName(String surname) {this.lastName = surname;}
    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    public List<Booking> getBookings() {
        return bookings;
    }
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}

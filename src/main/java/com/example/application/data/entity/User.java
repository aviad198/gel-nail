package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import com.example.application.data.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class User extends AbstractEntity {
    private String username;
    private String name;
    private String surname;
    @JsonIgnore
    private String hashedPassword;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Lob
    private String profilePictureUrl;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Contact> contact = new LinkedList<>();

//TO-DO not empty
    @Email
//    @NotNull
//    @NotEmpty
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {return surname;}
    public void setSurname(String surname) {this.surname = surname;}
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

}

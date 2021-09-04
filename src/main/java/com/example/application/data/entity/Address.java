package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.application.data.AbstractEntity;
import com.vaadin.flow.component.textfield.TextField;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Address extends AbstractEntity {

    @NotNull
    @NotEmpty
    private String street = "";


    private String postalCode = "";

    @NotNull
    @NotEmpty
    private String city = "";


    private String state ="";

    @NotNull
    @NotEmpty
    private String country="";


    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private Contact employees;



    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;



    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public Contact getEmployees() {
        return employees;
    }
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }


    @Override
    public String toString() {
        return street + ", " + city + ", "+ country;
    }


}

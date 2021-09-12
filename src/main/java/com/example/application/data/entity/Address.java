package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Address extends AbstractEntity {

    @NotNull
    @NotEmpty
    private String street = "";

    @NotNull
    @NotEmpty
    private String city = "";

    @NotNull
    @NotEmpty
    private String country="";


    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;


    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
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

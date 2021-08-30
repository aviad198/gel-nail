package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
//@Table(name = "services")
public class TypeService  extends AbstractEntity {

//    @NotNull
    private String name = "";

//    @NotEmpty
    private int price;

//    @NotEmpty
    private int time;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

//    @JoinColumn(name = "company_id")
    private Integer idCompany;


    public TypeService(String name, int price, int time, Integer idCompany,Company company){
        this.name = name;
        this.price = price;
        this.time = time;
        this.idCompany = idCompany;
        this.company = company;
    }

    public TypeService() {

    }

    @Column(name = "price", nullable = false)
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    @Column(name = "name_type", nullable = false)
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "time", nullable = false)
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

    @Column(name = "id_Company", nullable = false)
    public Integer getIDCompany() {
        return idCompany;
    }
    public void setIDCompany(Integer idCompany) {
        this.idCompany = idCompany;
    }

    @Column(name = "company", nullable = false)
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }


}



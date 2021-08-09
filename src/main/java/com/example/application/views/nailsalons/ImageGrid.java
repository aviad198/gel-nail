package com.example.application.views.nailsalons;

import com.example.application.data.entity.Company;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class ImageGrid extends VerticalLayout {

    public ImageGrid(List<Company> companyList){
        for(Company company : companyList){
        ImageCard item = new ImageCard(company.getName(), company.getMainImageURL());
        add(item);
       }
    }
    public void UpdateGrid(List<Company> companyList) {
        removeAll();
        for(Company company : companyList){
            ImageCard item = new ImageCard(company.getName(), company.getMainImageURL());
            add(item);
        }
    }
}

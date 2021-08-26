package com.example.application.views.nailsalons;

import com.example.application.data.entity.Company;
import com.example.application.views.cares.CompanyView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouteParameters;

import java.util.List;

public class ImageGrid extends VerticalLayout {

    public ImageGrid(List<Company> companyList){
        for(Company company : companyList){
        ImageCard item = new ImageCard(company.getName(), company.getMainImageURL());
        this.addClickListener(e -> openCompany(company.getId()));
        add(item);
       }
    }
    public void UpdateGrid(List<Company> companyList) {
        removeAll();
        for(Company company : companyList){
            ImageCard item = new ImageCard(company.getName(), company.getMainImageURL());
            item.getElement().addEventListener("open-company", e -> openCompany(company.getId()));
            add(item);
        }
    }
    private void openCompany(Integer id) {
        getUI().ifPresent(ui -> ui.navigate(
                        CompanyView.class,
                        new RouteParameters("companyID", String.valueOf(id))));
    }
}

package com.example.application.views.info;

import com.example.application.data.entity.Address;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.User;
import com.example.application.data.service.CompanyService;
import com.example.application.data.service.ContactService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "companies")
public class companyGrid extends VerticalLayout {

    private CompanyService companyService;

    private Grid<Company> grid = new Grid<>(Company.class);

    public companyGrid(CompanyService companyService) {
        this.companyService = companyService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(grid);
        updateList();
    }

    private void updateList() {
        grid.setItems(companyService.findAll());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("admin");
        grid.removeColumnByKey("address");
        grid.setColumns("name", "rating", "description");
        grid.addColumn(company -> {
            User user = company.getAdmin();
            return user == null ? "-" : user.getFirstName() + " " + user.getLastName();
        }).setHeader("Admin");
        grid.addColumn(company -> {
            Address address = company.getAddress();
            return address == null ? "-" : address.toString();
        }).setHeader("address");
    }
}

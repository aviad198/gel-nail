package com.example.application.views.info;

import com.example.application.data.entity.Address;
import com.example.application.data.entity.Booking;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.service.CompanyService;
import com.example.application.data.service.ContactService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "testing")
public class testingGrid extends VerticalLayout {

    private ContactService contactService;

    private Grid<Contact> grid = new Grid<>(Contact.class);

    public testingGrid(ContactService contactService) {
        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(grid);
        updateList();
    }

    private void updateList() {
        grid.setItems(contactService.findAll());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.removeColumnByKey("address");
        grid.setColumns("firstName", "lastName", "email", "status");
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");
        grid.addColumn(contact -> {
            Address address = contact.getAddress();
            return address == null ? "-" : address.toString();
        }).setHeader("address");
    }
}

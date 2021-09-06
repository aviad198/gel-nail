package com.example.application.views.info;

import com.example.application.data.entity.Address;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.User;
import com.example.application.data.service.AddressService;
import com.example.application.data.service.CompanyService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.example.application.views.nailsalons.NailSalonsView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RolesAllowed("admin")
@Route(value = "company-info", layout = MainLayout.class)
public class CompanyInfo extends VerticalLayout implements BeforeEnterObserver {
    private AuthenticatedUser authenticatedUser;
    private CompanyService companyService;
    private AddressService addressService;
    private Company company;
    private Address address;
    private TextField companyName = new TextField("Company name");
    private TextField companyEmail = new TextField("Email address");
    private TextField  street = new TextField("Street address");
    private TextField  city = new TextField("Citys");
    private TextField  country = new TextField("Country");
    /*
    private ComboBox<String> street = new ComboBox<>("Street address");
    private ComboBox<String> city = new ComboBox<>("City");
    private ComboBox<String>   country = new ComboBox<>("Country");
    */

    private TextField description = new TextField("Description");

    private Button save = new Button("Save");

    public CompanyInfo(AuthenticatedUser authenticatedUser, CompanyService companyService, AddressService addressService){
        this.authenticatedUser = authenticatedUser;
        this.companyService = companyService;
        this.addressService = addressService;
        setSizeFull();
        add(createTitle());
        configure();
        add(createFormLayout());
        add(createButtonLayout());
        save.addClickListener(e -> saveInfo());

    }
    private void configure(){
        Optional<User> authUser = authenticatedUser.get();
        company = authUser.get().getCompany();
        address = company.getAddress();
    }
    private Component createTitle() {
        return new H3("My company info");
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        return buttonLayout;
    }
    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        companyName.setValue(company.getName());
        companyEmail.setValue(company.getMail());
        companyEmail.setEnabled(false);
        description.setValue(company.getDescription());
        city.setValue(address.getCity());
        street.setValue(address.getStreet());
        country.setValue(address.getCountry());

        formLayout.add(companyName, companyEmail, description, city, street, country);
        return formLayout;
    }
    private void saveInfo() {
        company.setName(companyName.getValue());
        company.setDescription(description.getValue());
        companyService.update(company);

        address.setCity(city.getValue());
        address.setStreet(street.getValue());
        address.setCountry(country.getValue());
        addressService.update(address);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<User> authUser = authenticatedUser.get();
        if (!authUser.isPresent()) {
            event.forwardTo(NailSalonsView.class);
        }
    }
}

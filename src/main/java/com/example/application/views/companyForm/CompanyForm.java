package com.example.application.views.companyForm;

import com.example.application.data.entity.Company;
import com.example.application.data.service.CompanyService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Company Form")
@Route(value = "company-form-view", layout = MainLayout.class)
@AnonymousAllowed
public class CompanyForm extends VerticalLayout {
    private TextField companyName = new TextField("Company name");
    private NumberField phone = new NumberField("Phone number");
    private EmailField email = new EmailField("Email address");
    private TextField street = new TextField("Street address");
    private TextField city = new TextField("City");
    private TextField country = new TextField("Country");
    private TextField description = new TextField("Description");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Company> binder = new Binder<>(Company.class);

    private CompanyForm(CompanyService companyService){
        addClassName("company-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);

        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {companyService.update(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            clearForm();
        });
    }

    private Component createTitle() {
        return new H3(" Registration Company");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        //formLayout.add(description , 2);
        formLayout.add(companyName,phone, email, street, city, country,description);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private void clearForm() {
        this.binder.setBean(new Company());
    }
}

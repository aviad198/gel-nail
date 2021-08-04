package com.example.application.views.nailsalons;

import com.example.application.data.entity.Company;
import com.example.application.data.service.CompanyService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;


@PageTitle("Nail Salons")
@Route(value = "SalonList", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
@Tag("nail-salons-view")
@JsModule("./views/nailsalons/nail-salons-view.ts")
public class NailSalonsView extends LitTemplate implements HasComponents, HasStyle {

    @Id
    private Select<String> sortBy;
    private TextField filterText = new TextField();
    private CompanyService companyService;
    List<Company> companyList;
    public NailSalonsView(CompanyService companyService) {
        addClassNames("nail-salons-view", "flex", "flex-col", "h-full");
        sortBy.setItems("Popularity", "Newest first", "Oldest first");
        sortBy.setValue("Popularity");
        configureFilter();
        add(filterText);
        
        this.companyService = companyService;
        companyList = companyService.findAll();

        for(Company company : companyList){
            add(new ImageCard(company.getName(), company.getMainImageURL()));
        }
    }

    private void configureFilter() {
        filterText.setPlaceholder("Where?...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void updateList() {
        companyList = companyService.findAll(filterText.getValue());
    }
}
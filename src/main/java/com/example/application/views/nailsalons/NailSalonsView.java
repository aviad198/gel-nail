package com.example.application.views.nailsalons;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.TypeService;
import com.example.application.data.service.CompanyService;
import com.example.application.data.service.ServiceService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    private TextField location = new TextField();
    private TextField businessName = new TextField();
    private ComboBox<TypeService> services = new ComboBox<>();
    private Button startSearch = new Button("Search");
    private CompanyService companyService;
    private ServiceService serviceService;
    private ImageGrid imageGrid;
    List<Company> companyList;
    List<TypeService> serviceList;
    FormLayout layoutSearch = new FormLayout();

    public NailSalonsView(CompanyService companyService, ServiceService serviceService) {
        //get services
        this.companyService = companyService;
        this.serviceService = serviceService;
        //add CSS/JS files
        addClassNames("nail-salons-view", "flex", "flex-col", "h-full");
        //configure sort
        sortBy.setItems("Popularity", "Newest first", "Oldest first");
        sortBy.setValue("Popularity");

        configureFilter(serviceService);

        HorizontalLayout layoutSearch = new HorizontalLayout();
        VerticalLayout layout = new VerticalLayout();

        layoutSearch.add(businessName, services, location, startSearch);
        layoutSearch.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);

        layout.add(layoutSearch);

        companyList = companyService.findAll();
        imageGrid = new ImageGrid(companyList);
        layout.add(imageGrid);
        add(layout);
    }

    private void configureFilter(ServiceService serviceService) {
        location.setLabel("Location");
        location.setPlaceholder("Where?...");
        location.setClearButtonVisible(true);
        location.setValueChangeMode(ValueChangeMode.LAZY);
        location.addValueChangeListener(e -> updateList());

        serviceList = serviceService.findAll();
        services.setItems(serviceList);
        services.setItemLabelGenerator(TypeService::getName);

        services.setClearButtonVisible(true);
        services.setLabel("Services");

        businessName.setLabel("Business name");
        businessName.setClearButtonVisible(true);
        businessName.setValueChangeMode(ValueChangeMode.LAZY);
        businessName.addValueChangeListener(e -> updateList());

    }

    private void updateList() {
        companyList = companyService.findAll();
        imageGrid.UpdateGrid(companyList);
    }
}
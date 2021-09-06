package com.example.application.views.nailsalons;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.TypeService;
import com.example.application.data.service.AddressService;
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

    //@Id
   // private Select<String> sortBy;
    private TextField location = new TextField();
    private TextField businessName = new TextField();
    private ComboBox<TypeService> services = new ComboBox<>();
    private Button startSearch = new Button("Search");
    private CompanyService companyService;
    private ServiceService serviceService;
    private AddressService addressService;
    private ImageGrid imageGrid;
    List<Company> companyList;
    List<TypeService> serviceList;
    FormLayout layoutSearch = new FormLayout();

    public NailSalonsView(CompanyService companyService, ServiceService serviceService, AddressService addressService) {
        //get services
        this.companyService = companyService;
        this.serviceService = serviceService;
        this.addressService = addressService;
        //add CSS/JS files
        addClassNames("nail-salons-view", "flex", "flex-col", "h-full");
        //configure sort
        //sortBy.setItems("Popularity", "Newest first", "Oldest first");
       // sortBy.setValue("Popularity");

        configureFilter();
        add(configureLayout());
    }

    private VerticalLayout configureLayout(){
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout layoutSearch = new HorizontalLayout();
        layoutSearch.add(businessName, location, startSearch);
        layoutSearch.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);
        layout.add(layoutSearch);

        companyList = companyService.findAll();
        imageGrid = new ImageGrid(companyList);
        layout.add(imageGrid);
        layout.setWidthFull();

        return layout;
    }

    private void configureFilter() {
        location.setLabel("Location");
        location.setPlaceholder("Where?...");
        location.setClearButtonVisible(true);
        location.setValueChangeMode(ValueChangeMode.LAZY);
      //  location.addValueChangeListener(e -> updateByLocation());

        businessName.setLabel("Business name");
        businessName.setClearButtonVisible(true);
        businessName.setValueChangeMode(ValueChangeMode.LAZY);
 //       businessName.addValueChangeListener(e -> updateList());

        startSearch.addClickListener(e ->updateList1());


    }

    private void updateList() {
        companyList = companyService.findAll(location.getValue());
        imageGrid.UpdateGrid(companyList);
    }

    private void updateList1() {
        companyList = companyService.findAll(businessName.getValue());
        List<Company> tempCompanyList = addressService.findAll(location.getValue());
        tempCompanyList.removeAll(companyList);
        companyList.removeAll(tempCompanyList);
        imageGrid.UpdateGrid(companyList);
    }
    private void updateByLocation() {
        companyList = addressService.findAll(location.getValue());
        imageGrid.UpdateGrid(companyList);
    }
    private void updateByBusiness() {
        companyList = addressService.findAll(businessName.getValue());
        imageGrid.UpdateGrid(companyList);
    }

}
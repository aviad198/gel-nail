package com.example.application.views.nailsalons;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.TypeService;
import com.example.application.data.service.AddressService;
import com.example.application.data.service.CompanyService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
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

import java.util.LinkedList;
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
    private AddressService addressService;
    private ImageGrid imageGrid;
    List<Company> companyList;

    public NailSalonsView(CompanyService companyService, AddressService addressService) {
        //get services
        this.companyService = companyService;
        this.addressService = addressService;
        //add CSS/JS files
        addClassNames("nail-salons-view", "flex", "flex-col", "h-full");
        //configure sort
        sortBy.setItems("Popularity", "a-z", "z-a");
        //sortBy.setValue("Popularity");
        sortBy.addValueChangeListener(e -> updateBySort(e.getValue()));

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

        businessName.setLabel("Business name");
        businessName.setClearButtonVisible(true);
        businessName.setValueChangeMode(ValueChangeMode.LAZY);

        startSearch.addClickListener(e ->updateList());
    }

    private void updateList() {
        companyList = union(companyService.findAll(businessName.getValue()),companyService.findByAddress(location.getValue()));
        companyList = union(updateBySort2(sortBy.getValue()),companyList);
        imageGrid.UpdateGrid(companyList);

    }

    private void updateBySort(String s) {
        List<Company> tempList;
        switch(s) {
            case "a-z":
                tempList = companyService.findAllSortByNameAZ();
                break;
            case "z-a":
                tempList = companyService.findAllSortByNameZA();
                break;
            case "Popularity":
                tempList = companyService.findAllSortByRating();
                break;
            default:
                tempList = companyService.findAll();
        }
        imageGrid.UpdateGrid(union(tempList,this.companyList));
    }

    private List<Company> updateBySort2(String s) {
        switch(s) {
            case "a-z":
                return companyService.findAllSortByNameAZ();
            case "z-a":
                return companyService.findAllSortByNameZA();
            case "Popularity":
                return companyService.findAllSortByRating();
            default:
                return companyService.findAll();
        }
    }
    private List<Company> union(List<Company> list1,  List<Company> list2){

        List<Company> listA = new LinkedList<>(list1);
        List<Company> listB = new LinkedList<>(list2);
        listA.removeAll(list2);
        listB.removeAll(list1);
        listA.addAll(listB);

        List<Company> listTwoCopy = new LinkedList<>(list2);
        listTwoCopy.removeAll(list1);
        list1.addAll(listTwoCopy);

        list1.removeAll(listA);
        return list1;
    }
}
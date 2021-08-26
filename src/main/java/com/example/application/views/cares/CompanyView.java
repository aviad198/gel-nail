package com.example.application.views.cares;

import java.util.Optional;

import com.example.application.data.entity.Company;
import com.example.application.data.service.CompanyService;

import com.example.application.views.nailsalons.NailSalonsView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;

import javax.annotation.security.PermitAll;

import com.vaadin.flow.component.html.Image;

@PageTitle("Book a Care")
@Route(value = "Company/:companyID?/:action?(edit)", layout = MainLayout.class)
@AnonymousAllowed
public class CompanyView extends Div implements BeforeEnterObserver {

    private final String COMPANY_ID = "companyID";
    private final String COMPANY_EDIT_ROUTE_TEMPLATE = "Company/%d/edit";

    //private Grid<SampleFoodProduct> grid = new Grid<>(SampleFoodProduct.class, false);

    //    private Upload image;
    private Image companyImage;


//    private Button cancel = new Button("Cancel");
//    private Button save = new Button("Save");

    private BeanValidationBinder<Company> binder;

    private Company company;

    private Label companyName;

    private CompanyService companyService;

    private CompanyHeader companyHeader;

    public CompanyView(@Autowired CompanyService companyService) {
        this.companyService = companyService;
        addClassNames("cares-view", "flex", "flex-col", "h-full");

        // Create UI
        VerticalLayout verticalLayout = new VerticalLayout();

        createHeader(verticalLayout);
        createScheduleLayout(verticalLayout);
        //createEditorLayout(splitLayout);
        //add(companyImage);
        add(verticalLayout);

        // Configure Grid
        /*TemplateRenderer<SampleFoodProduct> imageRenderer = TemplateRenderer.<SampleFoodProduct>of(
                "<span style='border-radius: 50%; overflow: hidden; display: flex; align-items: center; justify-content: center; width: 64px; height: 64px'><img style='max-width: 100%' src='[[item.image]]' /></span>")
                .withProperty("image", SampleFoodProduct::getImage);
        grid.addColumn(imageRenderer).setHeader("Image").setWidth("96px").setFlexGrow(0);

        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("eanCode").setAutoWidth(true);
        grid.setItems(query -> sampleFoodProductService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent()
                        .navigate(String.format(SAMPLEFOODPRODUCT_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(CompanyView.class);
            }
        });*/

        // Configure Form
        binder = new BeanValidationBinder<>(Company.class);

        // Bind fields. This where you'd define e.g. validation rules

        //     binder.bindInstanceFields(this);
/*

        attachImageUpload(image, imagePreview);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.sampleFoodProduct == null) {
                    this.sampleFoodProduct = new SampleFoodProduct();
                }
                binder.writeBean(this.sampleFoodProduct);
                this.sampleFoodProduct.setImage(imagePreview.getSrc());

                sampleFoodProductService.update(this.sampleFoodProduct);
                clearForm();
                refreshGrid();
                Notification.show("SampleFoodProduct details stored.");
                UI.getCurrent().navigate(CompanyView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the sampleFoodProduct details.");
            }
        });
*/

    }

    private void createHeader(VerticalLayout verticalLayout) {
//        companyImage = new Image();
//        companyImage.setVisible(true);
//        companyImage.setMaxHeight(550, Unit.PIXELS);
//        companyName = new Label();
//        verticalLayout.add(companyImage);
//        verticalLayout.add(companyName);
        companyHeader = new CompanyHeader();
        verticalLayout.add(companyHeader);

    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> companyID = event.getRouteParameters().getInteger(COMPANY_ID);
        if (companyID.isPresent()) {
            Optional<Company> companyFromBackend = companyService
                    .get(companyID.get());
            if (companyFromBackend.isPresent()) {
                populatePage(companyFromBackend.get());
                //populateHeader();
            } else {
                Notification.show(String.format("The requested Company was not found, ID = %d",
                        companyID.get()), 3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                //refreshGrid();
                event.forwardTo(NailSalonsView.class);
            }
        }
    }

    private void populateHeader() {
        this.companyImage.setSrc(company.getMainImageURL());
        companyImage.setVisible(true);
        companyName.setText(company.getName());
    }


/*    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        Label imageLabel = new Label("Image");
        imagePreview = new Image();
        imagePreview.setWidth("100%");
        image = new Upload();
        image.getStyle().set("box-sizing", "border-box");
        image.getElement().appendChild(imagePreview.getElement());
        name = new TextField("Name");
        eanCode = new TextField("Ean Code");
        Component[] fields = new Component[]{imageLabel, image, name, eanCode};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }*/

/*    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }*/

    private void createScheduleLayout(VerticalLayout verticalLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        verticalLayout.add(wrapper);
        String[] dayOfWeek = {"sun", "mon", "tue", "wed", "thu", "fri", "sut"};
        HorizontalLayout her = new HorizontalLayout();

        for (String day : dayOfWeek) {
            VerticalLayout ver = new VerticalLayout();
            her.add(ver);
            Label dayLab = new Label(day);
            ver.add(dayLab);
            for (int i = 7; i < 25; i++) {
                Button time = new Button(i + ":00");
               /* time.addClickListener(e -> {
                    try {
                        if (this.company == null) {
                            this.company = new Company();
                        }
                        binder.writeBean(this.sampleFoodProduct);
                        this.sampleFoodProduct.setImage(imagePreview.getSrc());

                        sampleFoodProductService.update(this.sampleFoodProduct);
                        clearForm();
                        refreshGrid();
                        Notification.show("SampleFoodProduct details stored.");
                        UI.getCurrent().navigate(CompanyView.class);
                    } catch (ValidationException validationException) {
                        Notification.show("An exception happened while trying to store the sampleFoodProduct details.");
                    }
                });*/
                ver.add(time);
            }
        }
        wrapper.add(her);
    }

   /* private void attachImageUpload(Upload upload, Image preview) {
        ByteArrayOutputStream uploadBuffer = new ByteArrayOutputStream();
        upload.setAcceptedFileTypes("image/*");
        upload.setReceiver((fileName, mimeType) -> {
            return uploadBuffer;
        });
        upload.addSucceededListener(e -> {
            String mimeType = e.getMIMEType();
            String base64ImageData = Base64.getEncoder().encodeToString(uploadBuffer.toByteArray());
            String dataUrl = "data:" + mimeType + ";base64,"
                    + UriUtils.encodeQuery(base64ImageData, StandardCharsets.UTF_8);
            upload.getElement().setPropertyJson("files", Json.createArray());
            preview.setSrc(dataUrl);
            uploadBuffer.reset();
        });
        preview.setVisible(false);
    }*/

  /*  private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }*/

    private void clearForm() {
        populatePage(null);
    }

    private void populatePage(Company company) {
        this.company = company;
        binder.readBean(this.company);
        if (company == null) {
            //this.companyImage.setSrc("");
        } else {
            companyImage = new Image();
            this.companyImage.setSrc(company.getMainImageURL());
            companyImage.setVisible(true);
        companyHeader.setCompanyHeader(company.getMainImageURL(), "no text yet", company.getName(),"no sub yet", "no desc yet", "5");

        }

    }
}

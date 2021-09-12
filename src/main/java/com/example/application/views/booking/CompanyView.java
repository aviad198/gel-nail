package com.example.application.views.booking;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.User;
import com.example.application.data.service.BookingService;
import com.example.application.data.service.CompanyService;
import com.example.application.data.service.UserService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.example.application.views.nailsalons.NailSalonsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@PageTitle("Book a Care")
@Route(value = "Company/:companyID?/:action?(edit)", layout = MainLayout.class)
@AnonymousAllowed
public class CompanyView extends Div implements BeforeEnterObserver {

    private final String COMPANY_ID = "companyID";
    private final String COMPANY_EDIT_ROUTE_TEMPLATE = "Company/%d/edit";


    private Image companyImage;

    private ScheduleDialog scheduleDialog;

    private BeanValidationBinder<Company> binder;

    private Company company;

    private Label companyName;

    private CompanyService companyService;

    private CompanyHeader companyHeader;

    private AuthenticatedUser authenticatedUser;

    private UserService userService;

    private BookingService bookingService;

    private VerticalLayout verticalLayout;

    private Div wrapper;

    private LocalDateTime today;

    public CompanyView(@Autowired CompanyService companyService, AuthenticatedUser authenticatedUser, UserService userService, BookingService bookingService) {
        this.companyService = companyService;
        this.authenticatedUser = authenticatedUser;
        this.userService = userService;
        this.bookingService = bookingService;
        addClassNames("cares-view", "flex", "flex-col", "h-full");

        // Create UI
        verticalLayout = new VerticalLayout();
        createHeader(verticalLayout);

        //wrapper for sced
        //TODO user nicer use of object
        wrapper = new Div();
        wrapper.setId("schedule-wrapper");
        wrapper.setWidthFull();

        today = LocalDate.now().atTime(7, 0);
        verticalLayout.add(wrapper);
        add(verticalLayout);

        // Configure Form
        binder = new BeanValidationBinder<>(Company.class);
    }

    private void createHeader(VerticalLayout verticalLayout) {
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
                Company company = companyFromBackend.get();
                populatePage(company);
                createScheduleLayout();
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

    private void createScheduleLayout() {

        String[] dayOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        HorizontalLayout scheduleHorizontalLayout = new HorizontalLayout();

        Button prevWeekBtn = new Button("<");
        prevWeekBtn.addClickListener(click -> {
            today = today.minusDays(7);
            refreshSchedule();
        });
        Button nextWeekBtn = new Button(">");
        nextWeekBtn.addClickListener(click -> {
            today = today.plusDays(7);
            refreshSchedule();

        });

        scheduleHorizontalLayout.add(prevWeekBtn);

        LocalDateTime weekStart = today.minusDays(today.getDayOfWeek().getValue());
        for (int day = 0; day < 7; day++) {
            VerticalLayout dayLayout = new VerticalLayout();
            scheduleHorizontalLayout.add(dayLayout);
            LocalDateTime bookingDay = weekStart.plusDays(day);
            bookingDay.format(DateTimeFormatter.ISO_LOCAL_DATE);
            Label dayLab = new Label(dayOfWeek[day] + " " + bookingDay.getDayOfMonth() + "." + bookingDay.getMonthValue());
            dayLayout.add(dayLab);
            for (int time = 8; time < 20; time++) {
                LocalDateTime bookingDayTime = bookingDay.withHour(time);
                Button bookingDateBtn = new Button(bookingDayTime.getHour() + ":00");
                if (bookingService.isBooked(company, bookingDayTime) || bookingDayTime.isBefore(LocalDateTime.now())) {
                    bookingDateBtn.setEnabled(false);
                } else {
                    bookingDateBtn.addClickListener(click -> {
                        Optional<User> authUser = authenticatedUser.get();
                        if (authUser.isPresent()) {
                            User user = userService.get(authUser.get().getId()).get();
                            scheduleDialog.setTime(bookingDayTime);
                            scheduleDialog.setUser(user);
                            scheduleDialog.open();
                        } else {
                            Notification.show("Must signing to Book appointment");
                        }
                    });
                }
                dayLayout.add(bookingDateBtn);
            }
        }
        scheduleHorizontalLayout.add(nextWeekBtn);
        wrapper.add(scheduleHorizontalLayout);

    }

    public void refreshSchedule() {
        wrapper.removeAll();
        createScheduleLayout();
    }

    private void clearForm() {
        populatePage(null);
    }

    private void populatePage(Company company) {
        this.company = company;
        binder.readBean(this.company);
        if (company == null) {
           // this.companyImage.setSrc("");
        } else {
//            companyImage = new Image();
//            //this.companyImage.setSrc(company.getMainImageURL());
//            companyImage.setVisible(true);
            companyHeader.setCompanyHeader(company.getName(), company.getMainImageURL(), company.getDescription(), company.getAddress(), company.getRating());
            scheduleDialog = new ScheduleDialog(userService, companyService, bookingService, company, this);
        }

    }
}

package com.example.application.views.registerForm;

import com.example.application.data.Role;
import com.example.application.data.entity.Address;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.User;
import com.example.application.data.service.AddressService;
import com.example.application.data.service.CompanyService;
import com.example.application.data.service.UserService;
import com.example.application.security.SecurityConfiguration;
import com.example.application.views.MainLayout;
import com.example.application.views.login.LoginView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@PageTitle("Register Form")
@Route(value = "register-form", layout = MainLayout.class)
@AnonymousAllowed
public class RegisterForm extends VerticalLayout {

    private TextField username = new TextField("Username");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField email = new EmailField("Email address");
    private PasswordField hashedPassword = new PasswordField("Password");
    private PasswordField hashedPassword2 = new PasswordField("Re-enter password");

/*
    / This is a custom field we create to handle the field 'avatar' in our data. It
    // work just as any other field, e.g. the TextFields above. Instead of a String
    // value, it has an AvatarImage value.
    AvatarField avatarField = new AvatarField("Select Avatar image");
*/

    private Checkbox userTypeCB;
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    Span errorMessage = new Span();

    private UserService userService;
    /**
     * Flag for disabling first run for password validation
     */
    private boolean enablePasswordValidation;

    private BeanValidationBinder<User> userBinder = new BeanValidationBinder<>(User.class);
    private BeanValidationBinder<Company> companyBinder;
    private BeanValidationBinder<Address> addressBinder;

    /**
     * fields for new company
     */
    private TextField  companyName = new TextField("Company name");
    private EmailField companyEmail = new EmailField("Email address");
    private TextField  street = new TextField("Street address");
    private TextField  city = new TextField("City");
    private TextField  country = new TextField("Country");
    private TextField description = new TextField("Description");
    private CompanyService companyService;
    private AddressService addressService;

    public RegisterForm(@Autowired  UserService userService,@Autowired CompanyService companyService,@Autowired AddressService addressService) {
        this.userService = userService;
        this.companyService = companyService;
        this.addressService = addressService;

        addClassName("register-form");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        userTypeCB = new Checkbox("Are you a business owner?");
        userTypeCB.addValueChangeListener(marked -> {
            if (marked.getValue()) {
                remove(createButtonLayout());
                add(createCompanyLayout());
                add(createAddressLayout());
                add(createButtonLayout());
            } else {
                remove(createCompanyLayout());
                remove(createAddressLayout());
            }
        });
        userTypeCB.getStyle().set("padding-top", "10px");
        add(userTypeCB);
        add(errorMessage);
        clearForm();

        //shows status at error message span
        userBinder.setStatusLabel(errorMessage);

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> submitForm());

        userBinder.forField(hashedPassword).withValidator(this::passwordValidator).bind("hashedPassword");

        userBinder.forField(username).withValidator(this::userNameUniqueness).bind("username");

        userBinder.forField(email).withValidator(this::emailUniqueness).bind("email");

        //connect all fields to entity + validations
        userBinder.bindInstanceFields(this);

        //if second password insert start compare to first
        hashedPassword2.addValueChangeListener(e -> {
            // The user has modified the second field, now we can validate and show errors.
            // See passwordValidator() for how this flag is used.
            enablePasswordValidation = true;

            userBinder.validate();
        });
    }

    private void submitForm() {
        try {
            // Create empty bean to store the details into
            User user = new User();
            // Run validators and write the values to the bean
            userBinder.writeBean(user);

            //configure security to save password TO-DO(should be smoother)
            SecurityConfiguration securityConfiguration = new SecurityConfiguration();
            //set hash password
            user.setHashedPassword(securityConfiguration.passwordEncoder().encode(user.getHashedPassword()));
            //set role default
            user.setRoles(Collections.singleton(Role.USER));

            //set profile picture - TO-DO change profile picture
            user.setProfilePictureUrl("https://cdn.pixabay.com/photo/2017/06/13/12/53/profile-2398782_1280.png");

            if (userTypeCB.getValue()) {
                Address address = new Address();
                addressBinder.writeBean(address);
                addressService.update(address);

                Company company = new Company();
                companyBinder.writeBean(company);
                companyService.update(company);

                user.setRoles(Collections.singleton(Role.ADMIN));
                company.setAddress(address);
                address.setCompany(company);


                addressService.update(address);
                companyService.update(company);
                user.setCompany(company);
            }
            // Call backend to store the data
            userService.update(user);

            // Show success message if everything went well
            Notification.show(userBinder.getBean().getClass().getSimpleName() + " details stored.");


            getUI().ifPresent(ui -> ui.navigate(LoginView.class));
        } catch (ValidationException e1) {
            // validation errors are already visible for each field,
            // and bean-level errors are shown in the status label.

            // We could show additional messages here if we want, do logging, etc.

            //Notification.show(addressBinder.validate().getBeanValidationErrors().toString());
        }
/*            catch (ConstraintViolationException | TransactionSystemException e2){
            Notification.show(binder.validate().getBeanValidationErrors().toString());
            errorMessage.setText("Saving the data failed, please try again");
        }*/ catch (ServiceException e3) {
            // For some reason, the save failed in the back end.

            // First, make sure we store the error in the server logs (preferably using a
            // logging framework)
            e3.printStackTrace();

            // Notify, and let the user try again.
            Notification.show("Saving the data failed, please try again");
            errorMessage.setText("Saving the data failed, please try again");
        }
    }


    private ValidationResult passwordValidator(String pass1, ValueContext valueContext) {
        if (pass1 == null || pass1.length() < 8) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if (!enablePasswordValidation) {
            // user hasn't visited the field yet, so don't validate just yet, but next time.
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = hashedPassword2.getValue();

        if (pass1 != null && pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

    private ValidationResult userNameUniqueness(String userName, ValueContext valueContext) {
        if (userService.find(userName) != null) {
            return ValidationResult.error("Username already in use, pick a different one");
        }
        return ValidationResult.ok();
    }

    private ValidationResult emailUniqueness(String mail, ValueContext valueContext) {
        if (userService.findByEmail(mail) != null) {
            return ValidationResult.error("This mail is already registered");
        }
        return ValidationResult.ok();
    }

    private void clearForm() {

        userBinder.setBean(new User());
    }

    private Component createTitle() {
        return new H3("Signup form");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        //style error message
        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "15px 0");


        username.setRequired(true);
        firstName.setRequired(true);
        lastName.setRequired(true);
        email.setRequiredIndicatorVisible(true);
        hashedPassword.setRequired(true);

        formLayout.add(username, firstName, lastName, email, hashedPassword, hashedPassword2);


        return formLayout;
    }

    private Component createCompanyLayout() {
        FormLayout companyForm = new FormLayout();
        companyBinder = new BeanValidationBinder<>(Company.class);


        companyForm.add(companyName, companyEmail, description);

        companyBinder.forField(companyName).bind("name");
        companyBinder.forField(companyEmail).bind("mail");
        companyBinder.forField(description).bind("description");

        return companyForm;
    }

    private Component createAddressLayout() {
        FormLayout addressForm = new FormLayout();
        addressBinder = new BeanValidationBinder<>(Address.class);

        street.setRequired(true);
        city.setRequired(true);
        country.setRequired(true);

        addressForm.add(street, city, country);

        addressBinder.forField(street).bind("street");
        addressBinder.forField(city).bind("city");
        addressBinder.forField(country).bind("country");


        return addressForm;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }
}

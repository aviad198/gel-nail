package com.example.application.views.info;

import com.example.application.data.entity.User;
import com.example.application.data.service.UserService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RolesAllowed({"admin","user"})
@Route(value = "user-info", layout = MainLayout.class)
public class UserInfo extends VerticalLayout {
    private AuthenticatedUser authenticatedUser;
    private UserService userService;
    private TextField username = new TextField("Username");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email address");
    private Button save = new Button("Save");
    private User user;

    public UserInfo(AuthenticatedUser authenticatedUser, UserService userService){
        this.authenticatedUser = authenticatedUser;
        this.userService = userService;
        Optional<User> authUser = authenticatedUser.get();
        user = authUser.get();
        setSizeFull();
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        save.addClickListener(e -> saveInfo());
    }

    private void saveInfo() {
        user.setFirstName(firstName.getValue());
        user.setLastName(lastName.getValue());
        userService.update(user);
    }

    private Component createTitle() {
        return new H3("My info");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        username.setValue(user.getUsername());
        username.setEnabled(false);
        firstName.setValue(user.getFirstName());
        lastName.setValue(user.getLastName());
        email.setValue(user.getEmail());
        email.setEnabled(false);

        formLayout.add(username, firstName, lastName, email);
        return formLayout;
    }
    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        return buttonLayout;
    }
    /*
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<User> authUser = authenticatedUser.get();
        if (authUser.isPresent()) {
            user = this.userService.find(authUser.get().getUsername());
        }
        else{
            event.forwardTo(NailSalonsView.class);
        }
    }

     */
}

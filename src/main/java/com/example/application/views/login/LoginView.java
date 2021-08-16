package com.example.application.views.login;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Login")
@Route(value = "login")
public class LoginView extends VerticalLayout {
    public LoginView() {
        LoginForm login = new LoginForm();
        Anchor registerLink = new Anchor("register-form", "New user? Register");

        login.setAction("login");

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Gel Nail");
        i18n.getHeader().setDescription("Login using user/user or admin/admin");
        i18n.setAdditionalInformation(null);
        login.setI18n(i18n);

        login.setForgotPasswordButtonVisible(false);

        add(login, registerLink);
    }

}

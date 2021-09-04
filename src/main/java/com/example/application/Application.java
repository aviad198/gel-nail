package com.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.dependency.NpmPackage;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */

//disable Spring MVC auto-configuration on the Application class, as this interferes with how Vaadin works and can cause strange reloading behavior
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Theme(value = "gelnail")
@PWA(name = "Gel Nail", shortName = "Gel Nail", offlineResources = {"images/logo.png"})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "leaflet", version = "^1.7.1")
@NpmPackage(value = "@types/leaflet", version = "^1.5.23")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

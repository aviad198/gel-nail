package com.example.application.views.booking;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;

@JsModule("./views/nailsalons/image-card.ts")
@Tag("image-card")
public class CompanyHeader extends LitTemplate {


    @Id
    private Image image;

    @Id
    private Span header;

    @Id
    private Span subtitle;

    @Id
    private Paragraph text;

    @Id
    private Span badge;

    public CompanyHeader(){}

    public CompanyHeader(String url, String text, String name, String sub, String desc, String rating) {
        this.image.setSrc(url);
        this.image.setAlt(text);
        this.header.setText(name);
        this.subtitle.setText(sub);
        this.text.setText(desc);
        this.badge.setText(rating);
    }


    public void setCompanyHeader(String url, String text, String name, String sub, String desc, String rating) {
        this.image.setSrc(url);
        this.image.setAlt(text);
        this.header.setText(name);
        this.subtitle.setText(sub);
        this.text.setText(desc);
        this.badge.setText(rating);
    }
}

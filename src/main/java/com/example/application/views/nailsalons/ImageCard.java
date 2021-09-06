package com.example.application.views.nailsalons;

import com.example.application.data.entity.Address;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;

@JsModule("./views/nailsalons/image-card.ts")
@Tag("image-card")
public class ImageCard extends LitTemplate {


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

    public ImageCard(String text, String url) {
        this.image.setSrc(url);
        this.image.setAlt(text);
        this.header.setText("text");
        this.subtitle.setText("Card subtitle");
        this.text.setText(
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.");
        this.badge.setText("Label");
    }

    public ImageCard(String name, String mainImageURL, String description, Address address, int rating) {
        this.image.setSrc(mainImageURL==null? "" : mainImageURL);
        this.image.setAlt(name);
        this.header.setText(name);
        this.subtitle.setText(address==null? "" : address.toString());
        this.text.setText(description);
        this.badge.setText("*".repeat(rating));
    }
}

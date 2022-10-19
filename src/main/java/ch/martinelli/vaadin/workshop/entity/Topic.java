package ch.martinelli.vaadin.workshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

public enum Topic {

    SWOS("Softwareentwicklung mit Open Source"),
    MOBIOT("Mobile und IoT"),
    METSS("Methodik & Soft Skills"),
    BDAB("Big Data & Algorithmic Business"),
    OSSA("Open Source Systeme und Applikationen");

    private String name;

    Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

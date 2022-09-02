package ch.martinelli.wstage22.workshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Topic {

    SWOS("Softwareentwicklung mit Open Source"),
    MOBIOT("Mobile und IoT"),
    METHSK("Methodik & Soft Skills"),
    BDAB("Big Data & Algorithmic Business"),
    OSSA("Open Source Systeme und Applikationen"),
    ;

    private String name;

}

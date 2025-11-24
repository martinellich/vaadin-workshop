package ch.martinelli.vaadin.workshop.ui.views.workshop;

import org.springframework.context.ApplicationEvent;

public class WorkshopAdded extends ApplicationEvent {

    public WorkshopAdded(Object source) {
        super(source);
    }
}

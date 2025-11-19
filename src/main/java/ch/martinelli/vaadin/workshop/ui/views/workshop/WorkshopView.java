package ch.martinelli.vaadin.workshop.ui.views.workshop;

import ch.martinelli.vaadin.workshop.ui.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;

@PageTitle("Workshops")
@Route(value = "workshops", layout = MainLayout.class)
public class WorkshopView extends VerticalLayout implements BeforeEnterObserver {

    public WorkshopView() {
        add(new H1("Workshops"));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        add(new Span(LocalDateTime.now().toString()));
    }
}

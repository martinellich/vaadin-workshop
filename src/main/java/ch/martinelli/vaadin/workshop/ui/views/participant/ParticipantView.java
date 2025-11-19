package ch.martinelli.vaadin.workshop.ui.views.participant;

import ch.martinelli.vaadin.workshop.ui.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Participants")
@Route(value = "participants", layout = MainLayout.class)
public class ParticipantView extends VerticalLayout {

    public ParticipantView() {
        add(new H1("Participants"));
    }
}

package ch.martinelli.vaadin.workshop.ui.views.dashboard;

import ch.martinelli.vaadin.workshop.ui.views.MainLayout;
import ch.martinelli.vaadin.workshop.ui.views.workshop.WorkshopView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Dashboard")
@Route(value = "", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        add(new H1("Dashboard"));

        add(new RouterLink("Workshops", WorkshopView.class));
    }
}

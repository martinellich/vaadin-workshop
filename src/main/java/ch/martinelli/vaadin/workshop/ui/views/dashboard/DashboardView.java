package ch.martinelli.vaadin.workshop.ui.views.dashboard;

import ch.martinelli.vaadin.workshop.entity.Workshop;
import ch.martinelli.vaadin.workshop.repository.WorkshopRepository;
import ch.martinelli.vaadin.workshop.ui.views.MainLayout;
import ch.martinelli.vaadin.workshop.ui.views.workshop.WorkshopAdded;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.PageRequest;

@VaadinSessionScope
@AnonymousAllowed
@PageTitle("Dashboard")
@Route(layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class DashboardView extends VerticalLayout implements ApplicationListener<WorkshopAdded> {

    private final Grid<Workshop> grid = new Grid<>();
    private final WorkshopRepository workshopRepository;
    private final UI ui;

    public DashboardView(WorkshopRepository workshopRepository, ConfigurableApplicationContext applicationContext) {
        this.workshopRepository = workshopRepository;

        TextField filter = new TextField("Filter");
        filter.setValueChangeMode(ValueChangeMode.TIMEOUT);
        filter.addValueChangeListener(e -> loadData(e.getValue()));

        add(filter);

        grid.addColumn(Workshop::getTitle)
                .setHeader("Title")
                .setSortable(true).setSortProperty("title")
                .setWidth("300px");
        grid.addColumn(workshop -> workshop.getTopic().getName())
                .setHeader("Topic")
                .setSortable(true).setSortProperty("topic")
                .setAutoWidth(true);
        grid.addColumn(Workshop::getInstructor)
                .setHeader("Instructor")
                .setSortable(true).setSortProperty("instructor")
                .setAutoWidth(true);
        grid.addColumn(Workshop::getExecutionDate)
                .setHeader("Date")
                .setSortable(true).setSortProperty("executionDate")
                .setAutoWidth(true);
        grid.addColumn(Workshop::getStatus)
                .setHeader("Status")
                .setSortable(true).setSortProperty("status")
                .setAutoWidth(true);

        grid.setMultiSort(true);
        grid.setColumnReorderingAllowed(true);
        grid.setHeightFull();

        loadData("");

        add(grid);

        setHeightFull();

        ui = UI.getCurrent();

        applicationContext.addApplicationListener(this);
    }

    private void loadData(String title) {
        grid.setItems(query -> workshopRepository.findAllByTitleContainsIgnoreCase(
                        PageRequest.of(query.getPage(), query.getPageSize(),
                                VaadinSpringDataHelpers.toSpringDataSort(query)), title)
                .stream());
    }

    @Override
    public void onApplicationEvent(WorkshopAdded event) {
        ui.access(() -> grid.getDataProvider().refreshAll());
    }
}

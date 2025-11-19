package ch.martinelli.vaadin.workshop.ui.views.workshop;

import ch.martinelli.vaadin.workshop.entity.Workshop;
import ch.martinelli.vaadin.workshop.repository.WorkshopRepository;
import ch.martinelli.vaadin.workshop.ui.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;

@PageTitle("Workshops")
@Route(layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class WorkshopView extends VerticalLayout implements AfterNavigationObserver {

    private final Grid<Workshop> grid = new Grid<>();
    private final WorkshopRepository workshopRepository;

    public WorkshopView(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;

        setHeightFull();

        createFilter();
        createGrid();
    }

    private void createFilter() {
        TextField filter = new TextField("Filter");
        filter.setValueChangeMode(ValueChangeMode.TIMEOUT);
        filter.addValueChangeListener(e -> loadData(e.getValue()));

        add(filter);
    }

    private void createGrid() {
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
        grid.addComponentColumn(workshop -> {
                    Span pending = new Span(workshop.getStatus().name());
                    switch (workshop.getStatus()) {
                        case OPEN -> pending.getElement().getThemeList().add("badge");
                        case FULL -> pending.getElement().getThemeList().add("badge error");
                        case CANCELED -> pending.getElement().getThemeList().add("badge warning");
                    }
                    return pending;
                })
                .setHeader("Status")
                .setKey("status")
                .setSortable(true).setSortProperty("status")
                .setAutoWidth(true);

        grid.setMultiSort(true);
        grid.setColumnReorderingAllowed(true);
        grid.setHeightFull();

        add(grid);
    }

    private void loadData(String title) {
        grid.setItemsPageable(pageable -> workshopRepository.findAllByTitleContainsIgnoreCase(pageable, title).getContent());
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        loadData("");
    }

}

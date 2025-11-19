package ch.martinelli.vaadin.workshop.ui.views.workshop;

import ch.martinelli.vaadin.workshop.entity.Status;
import ch.martinelli.vaadin.workshop.entity.Topic;
import ch.martinelli.vaadin.workshop.entity.Workshop;
import ch.martinelli.vaadin.workshop.repository.WorkshopRepository;
import ch.martinelli.vaadin.workshop.ui.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;

@PageTitle("Workshops")
@Route(layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class WorkshopView extends VerticalLayout implements AfterNavigationObserver {

    private final Grid<Workshop> grid = new Grid<>();
    private final Binder<Workshop> binder = new Binder<>(Workshop.class);
    private final WorkshopRepository workshopRepository;
    private Workshop workshop;

    public WorkshopView(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;

        setHeightFull();

        createFilter();
        createGrid();
        createForm();
    }

    private void createFilter() {
        TextField filter = new TextField("Filter");
        filter.setValueChangeMode(ValueChangeMode.TIMEOUT);
        filter.addValueChangeListener(e -> loadData(e.getValue()));

        add(filter);
    }

    private void createGrid() {
        grid.setHeightFull();

        grid.addColumn(Workshop::getTitle)
                .setHeader("Title")
                .setSortable(true).setSortProperty("title")
                .setWidth("300px");
        grid.addColumn(w -> w.getTopic().getName())
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
        grid.addComponentColumn(w -> {
                    Span badge = new Span(w.getStatus().name());
                    switch (w.getStatus()) {
                        case OPEN -> badge.getElement().getThemeList().add("badge");
                        case FULL -> badge.getElement().getThemeList().add("badge error");
                        case CANCELED -> badge.getElement().getThemeList().add("badge warning");
                    }
                    return badge;
                })
                .setHeader("Status")
                .setSortable(true).setSortProperty("status")
                .setAutoWidth(true);

        grid.setMultiSort(true);
        grid.setColumnReorderingAllowed(true);

        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(w -> {
            this.workshop = w;
            binder.readBean(this.workshop);
        }));

        add(grid);
    }

    private void createForm() {
        this.workshop = new Workshop();
        binder.readBean(this.workshop);

        FormLayout formLayout = new FormLayout();

        TextField title = new TextField("Title");
        binder.forField(title)
                .asRequired()
                .bind(Workshop::getTitle, Workshop::setTitle);

        Select<Topic> topic = new Select<>();
        topic.setLabel("Topic");
        topic.setItems(Topic.values());
        topic.setItemLabelGenerator(Topic::getName);
        binder.forField(topic)
                .asRequired()
                .bind(Workshop::getTopic, Workshop::setTopic);

        TextField instructor = new TextField("Instructor");
        binder.forField(instructor)
                .asRequired()
                .bind(Workshop::getInstructor, Workshop::setInstructor);

        Select<Status> status = new Select<>();
        status.setLabel("Status");
        status.setItems(Status.values());
        binder.forField(status)
                .asRequired()
                .bind(Workshop::getStatus, Workshop::setStatus);

        DatePicker date = new DatePicker("Date");
        binder.forField(date)
                .asRequired()
                .bind(Workshop::getExecutionDate, Workshop::setExecutionDate);

        formLayout.add(title, topic, instructor, status, date);
        add(formLayout);

        Button save = new Button("Save", e -> {
            if (binder.writeBeanIfValid(this.workshop)) {
                workshopRepository.save(this.workshop);

                grid.getDataProvider().refreshAll();
                grid.getSelectionModel().select(null);

                this.workshop = new Workshop();
                binder.readBean(this.workshop);
            }
        });

        add(new HorizontalLayout(save));
    }

    private void loadData(String title) {
        grid.setItemsPageable(pageable -> workshopRepository.findAllByTitleContainsIgnoreCase(pageable, title));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        loadData("");
    }
}

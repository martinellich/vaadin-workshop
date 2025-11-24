package ch.martinelli.vaadin.workshop.ui.views.workshop;

import ch.martinelli.vaadin.workshop.entity.Status;
import ch.martinelli.vaadin.workshop.entity.Topic;
import ch.martinelli.vaadin.workshop.entity.Workshop;
import ch.martinelli.vaadin.workshop.repository.WorkshopRepository;
import ch.martinelli.vaadin.workshop.ui.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;

import java.text.DateFormatSymbols;
import java.util.Arrays;

@PermitAll
@PageTitle("Workshops")
@Route(value = "workshops", layout = MainLayout.class)
public class WorkshopView extends VerticalLayout {

    private final Grid<Workshop> grid = new Grid<>();
    private final BeanValidationBinder<Workshop> binder = new BeanValidationBinder<>(Workshop.class);
    private final WorkshopRepository workshopRepository;

    public WorkshopView(WorkshopRepository workshopRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.workshopRepository = workshopRepository;

        setHeightFull();

        TextField filter = new TextField("Filter");
        filter.setValueChangeMode(ValueChangeMode.TIMEOUT);
        filter.addValueChangeListener(e -> loadData(e.getValue()));

        add(filter);

        grid.addColumn(Workshop::getTitle)
                .setHeader(getTranslation("title"))
                .setSortable(true).setSortProperty("title")
                .setWidth("300px");
        grid.addColumn(workshop -> workshop.getTopic().getName())
                .setHeader(getTranslation("topic"))
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

        grid.addSelectionListener(event -> {
            event.getFirstSelectedItem().ifPresent(binder::setBean);
        });

        add(grid);

        binder.setBean(new Workshop());

        FormLayout formLayout = new FormLayout();

        TextField title = new TextField("Title");
        binder.forField(title).bind("title");

        Select<Topic> topic = new Select<>();
        topic.setLabel("Topic");
        topic.setItems(Topic.values());
        topic.setItemLabelGenerator(Topic::getName);
        binder.forField(topic).bind("topic");

        TextField instructor = new TextField("Instructor");
        binder.forField(instructor).bind("instructor");

        Select<Status> status = new Select<>();
        status.setLabel("Status");
        status.setItems(Status.values());
        binder.forField(status).bind("status");

        DatePicker date = new DatePicker("Date");
        var symbols = new DateFormatSymbols(UI.getCurrent().getLocale());
        var datePickerI18n = new DatePicker.DatePickerI18n();
        datePickerI18n.setMonthNames(Arrays.asList(symbols.getMonths()));
        datePickerI18n.setFirstDayOfWeek(1);
        datePickerI18n.setWeekdays(Arrays.stream(symbols.getWeekdays()).filter(s -> !s.isEmpty()).toList());
        datePickerI18n.setWeekdaysShort(Arrays.stream(symbols.getShortWeekdays()).filter(s -> !s.isEmpty()).toList());
        date.setI18n(datePickerI18n);
        binder.forField(date).bind("executionDate");

        formLayout.add(title, topic, instructor, status, date);
        add(formLayout);

        Button save = new Button("Save", e -> {
            workshopRepository.save(binder.getBean());
            grid.getDataProvider().refreshAll();
            grid.getSelectionModel().select(null);
            binder.setBean(new Workshop());

            applicationEventPublisher.publishEvent(new WorkshopAdded(this));
        });

        add(new HorizontalLayout(save));
    }

    private void loadData(String title) {
        grid.setItems(query -> workshopRepository.findAllByTitleContainsIgnoreCase(
                        PageRequest.of(query.getPage(), query.getPageSize(),
                                VaadinSpringDataHelpers.toSpringDataSort(query)), title)
                .stream());
    }
}

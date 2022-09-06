package ch.martinelli.wstage22.workshop.ui.views.participant;

import ch.martinelli.wstage22.workshop.entity.Participant;
import ch.martinelli.wstage22.workshop.entity.Workshop;
import ch.martinelli.wstage22.workshop.repository.ParticipantRepository;
import ch.martinelli.wstage22.workshop.repository.WorkshopRepository;
import ch.martinelli.wstage22.workshop.ui.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.data.domain.PageRequest;

@PageTitle("Participants")
@Route(layout = MainLayout.class)
public class ParticipantView extends VerticalLayout {

    private final Grid<Participant> grid = new Grid<>();

    private final BeanValidationBinder<Participant> binder = new BeanValidationBinder<>(Participant.class);

    public ParticipantView(ParticipantRepository participantRepository, WorkshopRepository workshopRepository) {
        grid.addColumn(Participant::getFirstName)
                .setHeader("First Name")
                .setSortable(true).setSortProperty("firstName");
        grid.addColumn(Participant::getLastName)
                .setHeader("Last Name")
                .setSortable(true).setSortProperty("lastName");
        grid.addColumn(Participant::getEmail)
                .setHeader("E-Mail")
                .setSortable(true).setSortProperty("email");
        grid.addColumn(participant -> participant.getWorkshop() != null ? participant.getWorkshop().getTitle() : "")
                .setHeader("Workshop");

        grid.setMultiSort(true);

        grid.setItems(query -> participantRepository.findAll(
                        PageRequest.of(query.getPage(), query.getPageSize(),
                                VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());

        grid.addSelectionListener(event -> {
            event.getFirstSelectedItem().ifPresent(binder::setBean);
        });

        add(grid);

        binder.setBean(new Participant());

        FormLayout formLayout = new FormLayout();

        TextField firstName = new TextField("First Name");
        binder.forField(firstName).bind("firstName");

        TextField lastName = new TextField("Last Name");
        binder.forField(lastName).bind("lastName");

        TextField email = new TextField("Email");
        binder.forField(email).bind("email");

        ComboBox<Workshop> workshop = new ComboBox<>("Workshop");
        workshop.setItems(workshopRepository.findAll());
        binder.forField(workshop).bind("workshop");

        formLayout.add(firstName, lastName, email, workshop);
        add(formLayout);

        Button save = new Button("Save", e -> {
            participantRepository.save(binder.getBean());
            grid.getDataProvider().refreshAll();
            grid.getSelectionModel().select(null);
            binder.setBean(new Participant());
        });

        add(new HorizontalLayout(save));
    }
}

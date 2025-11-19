package ch.martinelli.vaadin.workshop.ui.views.participant;

import ch.martinelli.vaadin.workshop.entity.Participant;
import ch.martinelli.vaadin.workshop.entity.Workshop;
import ch.martinelli.vaadin.workshop.repository.ParticipantRepository;
import ch.martinelli.vaadin.workshop.repository.WorkshopRepository;
import ch.martinelli.vaadin.workshop.ui.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Participants")
@Route(layout = MainLayout.class)
public class ParticipantView extends VerticalLayout implements AfterNavigationObserver {

    private final ParticipantRepository participantRepository;
    private final WorkshopRepository workshopRepository;
    private final Grid<Participant> grid = new Grid<>();

    private final Binder<Participant> binder = new Binder<>(Participant.class);
    private Participant participant;

    public ParticipantView(ParticipantRepository participantRepository, WorkshopRepository workshopRepository) {
        this.participantRepository = participantRepository;
        this.workshopRepository = workshopRepository;

        createGrid();
        createForm();
    }

    private void createGrid() {
        grid.addColumn(Participant::getFirstName)
                .setHeader("First Name")
                .setSortable(true).setSortProperty("firstName");
        grid.addColumn(Participant::getLastName)
                .setHeader("Last Name")
                .setSortable(true).setSortProperty("lastName");
        grid.addColumn(Participant::getEmail)
                .setHeader("E-Mail")
                .setSortable(true).setSortProperty("email");
        grid.addColumn(p -> p.getWorkshop() != null ? p.getWorkshop().getTitle() : "")
                .setHeader("Workshop");

        grid.setMultiSort(true);

        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(p -> {
            this.participant = p;
            binder.readBean(this.participant);
        }));

        add(grid);
    }

    private void createForm() {
        this.participant = new Participant();
        binder.readBean(this.participant);

        FormLayout formLayout = new FormLayout();

        TextField firstName = new TextField("First Name");
        binder.forField(firstName)
                .asRequired()
                .bind(Participant::getFirstName, Participant::setFirstName);

        TextField lastName = new TextField("Last Name");
        binder.forField(lastName)
                .asRequired()
                .bind(Participant::getLastName, Participant::setLastName);

        TextField email = new TextField("Email");
        binder.forField(email)
                .asRequired()
                .bind(Participant::getEmail, Participant::setEmail);

        ComboBox<Workshop> cbWorkshop = new ComboBox<>("Workshop");
        cbWorkshop.setItemsPageable(workshopRepository::findAllByTitleContainsIgnoreCase);
        cbWorkshop.setItemLabelGenerator(Workshop::getTitle);
        binder.forField(cbWorkshop)
                .asRequired()
                .bind(Participant::getWorkshop, Participant::setWorkshop);

        formLayout.add(firstName, lastName, email, cbWorkshop);

        add(formLayout);

        Button save = new Button("Save", e -> {
            if (binder.writeBeanIfValid(this.participant)) {
                participantRepository.save(this.participant);

                grid.getDataProvider().refreshAll();
                grid.getSelectionModel().select(null);

                this.participant = new Participant();
                binder.readBean(this.participant);
            }
        });

        add(new HorizontalLayout(save));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        grid.setItemsPageable(pageable -> participantRepository.findAll(pageable).getContent());

    }
}

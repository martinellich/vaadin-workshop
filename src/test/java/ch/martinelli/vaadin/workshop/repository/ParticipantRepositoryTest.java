package ch.martinelli.vaadin.workshop.repository;

import ch.martinelli.vaadin.workshop.entity.Workshop;
import ch.martinelli.vaadin.workshop.entity.Participant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ParticipantRepositoryTest {

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private WorkshopRepository workshopRepository;

    @Test
    void create_participant_and_assign_workshop() {
        Workshop workshop = workshopRepository.getReferenceById(1);

        Participant participant = new Participant();
        participant.setFirstName("Peter");
        participant.setLastName("Muster");
        participant.setEmail("peter.muster@schweiz.ch");
        participant.setWorkshop(workshop);

        participant = participantRepository.saveAndFlush(participant);

        assertThat(participant.getId()).isNotNull();
    }

}

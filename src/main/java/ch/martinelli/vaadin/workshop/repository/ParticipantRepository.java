package ch.martinelli.vaadin.workshop.repository;

import ch.martinelli.vaadin.workshop.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
}

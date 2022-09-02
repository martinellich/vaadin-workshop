package ch.martinelli.wstage22.workshop.repository;

import ch.martinelli.wstage22.workshop.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
}

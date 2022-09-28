package ch.martinelli.vaadin.workshop.repository;

import ch.martinelli.vaadin.workshop.entity.Workshop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkshopRepository extends JpaRepository<Workshop, Integer> {

    List<Workshop> findAllByTitleContainsIgnoreCase(Pageable pageable, String tile);
}

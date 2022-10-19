package ch.martinelli.vaadin.workshop.repository;

import ch.martinelli.vaadin.workshop.entity.Workshop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<Workshop, Integer> {

    Page<Workshop> findAllByTitleContainsIgnoreCase(Pageable pageable, String title);
}

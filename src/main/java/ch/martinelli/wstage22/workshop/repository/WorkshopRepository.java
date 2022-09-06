package ch.martinelli.wstage22.workshop.repository;

import ch.martinelli.wstage22.workshop.entity.Workshop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkshopRepository extends JpaRepository<Workshop, Integer> {

    List<Workshop> findAllByTitleContainsIgnoreCase(Pageable pageable, String tile);
}

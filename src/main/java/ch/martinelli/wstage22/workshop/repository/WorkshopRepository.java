package ch.martinelli.wstage22.workshop.repository;

import ch.martinelli.wstage22.workshop.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<Workshop, Integer> {
}

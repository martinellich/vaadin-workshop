package ch.martinelli.wstage22.workshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workshop_seq")
    @SequenceGenerator(name = "workshop_seq", sequenceName = "workshop_seq")
    private Integer id;

    private String title;
    private String topic;
    private String instructor;
    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN;
    private LocalDate executionDate;
}

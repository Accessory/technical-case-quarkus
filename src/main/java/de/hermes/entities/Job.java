package de.hermes.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class Job extends PanacheEntityBase {

    public Job(){}
    public Job(Instant timestamp, Integer commands, Integer result, Double duration) {
        this.timestamp = timestamp;
        this.commands = commands;
        this.result = result;
        this.duration = duration;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public Instant timestamp;
    public Integer commands;
    public Integer result;
    public Double duration;

}

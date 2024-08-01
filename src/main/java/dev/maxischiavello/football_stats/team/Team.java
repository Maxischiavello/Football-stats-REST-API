package dev.maxischiavello.football_stats.team;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private Integer id;
}

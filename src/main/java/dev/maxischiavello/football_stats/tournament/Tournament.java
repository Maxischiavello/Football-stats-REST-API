package dev.maxischiavello.football_stats.tournament;

import dev.maxischiavello.football_stats.match.Match;
import dev.maxischiavello.football_stats.team.Team;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany
    private List<Team> teams;
    @OneToMany
    private List<Match> matches;

    public Tournament() {
    }

    public Tournament(Integer id, String name, List<Team> teams, List<Match> matches) {
        this.id = id;
        this.name = name;
        this.teams = teams;
        this.matches = matches;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teams=" + teams +
                ", matches=" + matches +
                '}';
    }
}
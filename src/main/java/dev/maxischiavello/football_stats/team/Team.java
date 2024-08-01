package dev.maxischiavello.football_stats.team;

import dev.maxischiavello.football_stats.match.Match;
import dev.maxischiavello.football_stats.player.Player;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @OneToMany
    private List<Player> players;
    private Match match;
    private Integer points;
    private Integer goalsScored;
    private Integer goalsConceded;

    public Team() {}

    public Team(Integer id, String name, List<Player> players, Match match, Integer points, Integer goalsScored, Integer goalsConceded) {
        this.id = id;
        this.name = name;
        this.players = players;
        this.match = match;
        this.points = points;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(Integer goalsScored) {
        this.goalsScored = goalsScored;
    }

    public Integer getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(Integer goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", players=" + players +
                ", match=" + match +
                ", points=" + points +
                ", goalsScored=" + goalsScored +
                ", goalsConceded=" + goalsConceded +
                '}';
    }
}

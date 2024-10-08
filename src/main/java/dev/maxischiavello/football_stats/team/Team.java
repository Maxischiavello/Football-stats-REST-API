package dev.maxischiavello.football_stats.team;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.maxischiavello.football_stats.match.Match;
import dev.maxischiavello.football_stats.player.Player;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Team name must not be empty")
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Player> players = new ArrayList<>();


    @ManyToMany(mappedBy = "teams", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Match> matches = new ArrayList<>();

    private Integer points;

    @Column(name = "goals_scored")
    private Integer goalsScored;

    @Column(name = "goals_conceded")
    private Integer goalsConceded;

    @Column(name = "is_active")
    private Boolean isActive = true;

    public Team() {
    }

    public Team(Integer id, String name, List<Player> players, List<Match> matches, Integer points, Integer goalsScored, Integer goalsConceded) {
        this.id = id;
        this.name = name;
        this.players = players;
        this.matches = matches;
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

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", players=" + players +
                ", matches=" + matches +
                ", points=" + points +
                ", goalsScored=" + goalsScored +
                ", goalsConceded=" + goalsConceded +
                '}';
    }
}

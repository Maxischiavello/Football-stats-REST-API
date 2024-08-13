package dev.maxischiavello.football_stats.match;

import dev.maxischiavello.football_stats.result.Result;
import dev.maxischiavello.football_stats.team.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "match_team",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    @NotNull(message = "Teams must not be null")
    private List<Team> teams = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "result_id", referencedColumnName = "id")
    @NotNull(message = "Result must not be null")
    private Result result = new Result();

    @Column(name = "match_date")
    @NotNull(message = "date must not be null")
    private LocalDateTime date;

    public Match() {
    }

    public Match(Integer id, List<Team> teams, Result result, LocalDateTime date) {
        this.id = id;
        this.teams = teams;
        this.result = result;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", teams=" + teams +
                ", result=" + result +
                ", date=" + date +
                '}';
    }
}

package dev.maxischiavello.football_stats.match;

import dev.maxischiavello.football_stats.player.Player;
import dev.maxischiavello.football_stats.result.Result;
import dev.maxischiavello.football_stats.substitution.Substitution;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue
    private Integer id;
    private Result result;
    private Date date;
    private List<Player> startingPlayers;
    private List<Player> substitutePlayers;
    private List<Substitution> substitutions;

    public Match() {
    }

    public Match(Integer id, Result result, Date date, List<Player> startingPlayers, List<Player> substitutePlayers, List<Substitution> substitutions) {
        this.id = id;
        this.result = result;
        this.date = date;
        this.startingPlayers = startingPlayers;
        this.substitutePlayers = substitutePlayers;
        this.substitutions = substitutions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Player> getStartingPlayers() {
        return startingPlayers;
    }

    public void setStartingPlayers(List<Player> startingPlayers) {
        this.startingPlayers = startingPlayers;
    }

    public List<Player> getSubstitutePlayers() {
        return substitutePlayers;
    }

    public void setSubstitutePlayers(List<Player> substitutePlayers) {
        this.substitutePlayers = substitutePlayers;
    }

    public List<Substitution> getSubstitutions() {
        return substitutions;
    }

    public void setSubstitutions(List<Substitution> substitutions) {
        this.substitutions = substitutions;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", result=" + result +
                ", date=" + date +
                ", startingPlayers=" + startingPlayers +
                ", substitutePlayers=" + substitutePlayers +
                ", substitutions=" + substitutions +
                '}';
    }
}

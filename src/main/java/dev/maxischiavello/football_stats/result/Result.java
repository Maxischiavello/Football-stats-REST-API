package dev.maxischiavello.football_stats.result;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.maxischiavello.football_stats.game_actions.GameAction;
import dev.maxischiavello.football_stats.match.Match;
import dev.maxischiavello.football_stats.substitution.Substitution;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "local_team_scores")
    private Integer localTeamScores;

    @Column(name = "visit_team_scores")
    private Integer visitTeamScores;

    @OneToOne(mappedBy = "result", fetch = FetchType.EAGER)
    @JsonBackReference
    private Match match;
    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<GameAction> gameActions = new ArrayList<>();

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Substitution> substitutions = new ArrayList<>();


    public Result() {
    }

    public Result(Integer id, Integer localTeamScores, Integer visitTeamScores, Match match, List<GameAction> gameActions, List<Substitution> substitutions) {
        this.id = id;
        this.localTeamScores = localTeamScores;
        this.visitTeamScores = visitTeamScores;
        this.match = match;
        this.gameActions = gameActions;
        this.substitutions = substitutions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocalTeamScores() {
        return localTeamScores;
    }

    public void setLocalTeamScores(Integer localTeamScores) {
        this.localTeamScores = localTeamScores;
    }

    public Integer getVisitTeamScores() {
        return visitTeamScores;
    }

    public void setVisitTeamScores(Integer visitTeamScores) {
        this.visitTeamScores = visitTeamScores;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public List<GameAction> getGameActions() {
        return gameActions;
    }

    public void setGameActions(List<GameAction> gameActions) {
        this.gameActions = gameActions;
    }

    public List<Substitution> getSubstitutions() {
        return substitutions;
    }

    public void setSubstitutions(List<Substitution> substitutions) {
        this.substitutions = substitutions;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", localTeamScores=" + localTeamScores +
                ", visitTeamScores=" + visitTeamScores +
                ", match=" + match +
                ", gameActions=" + gameActions +
                ", substitutions=" + substitutions +
                '}';
    }
}


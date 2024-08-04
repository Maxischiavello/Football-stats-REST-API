package dev.maxischiavello.football_stats.result;

import dev.maxischiavello.football_stats.game_actions.GameAction;
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

    @OneToMany
    @JoinColumn(name = "result_id")
    @Column(name = "game_actions")
    private List<GameAction> gameActions = new ArrayList<>();

    public Result() {
    }

    public Result(Integer id, Integer localTeamScores, Integer visitTeamScores, List<GameAction> gameActions) {
        this.id = id;
        this.localTeamScores = localTeamScores;
        this.visitTeamScores = visitTeamScores;
        this.gameActions = gameActions;
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

    public List<GameAction> getGameActions() {
        return gameActions;
    }

    public void setGameActions(List<GameAction> gameActions) {
        this.gameActions = gameActions;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", localTeamScores=" + localTeamScores +
                ", visitTeamScores=" + visitTeamScores +
                ", gameActions=" + gameActions +
                '}';
    }
}

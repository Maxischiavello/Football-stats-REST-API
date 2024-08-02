package dev.maxischiavello.football_stats.result;

import dev.maxischiavello.football_stats.game_actions.GameAction;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer localTeamScores;
    private Integer visitTeamScores;
    @OneToMany(mappedBy = "result")
    private List<GameAction> gameActions;

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

package dev.maxischiavello.football_stats.game_actions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.maxischiavello.football_stats.player.Player;
import dev.maxischiavello.football_stats.result.Result;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "game_action")
public class GameAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Minute must not be null")
    private Integer minute;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Action must not be null")
    private Action action;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "result_id")
    @JsonBackReference
    private Result result;

    public GameAction() {
    }

    public GameAction(Integer id, Integer minute, Action action, Player player, Result result) {
        this.id = id;
        this.minute = minute;
        this.action = action;
        this.player = player;
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "GameAction{" +
                "id=" + id +
                ", minute=" + minute +
                ", action=" + action +
                ", player=" + player +
                ", result=" + result +
                '}';
    }
}
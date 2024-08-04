package dev.maxischiavello.football_stats.game_actions;

import dev.maxischiavello.football_stats.player.Player;
import jakarta.persistence.*;

@Entity
@Table(name = "game_action")
public class GameAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer minute;
    @Enumerated(EnumType.STRING)
    private Action action;
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    public GameAction() {
    }

    public GameAction(Integer id, Integer minute, Action action, Player player) {
        this.id = id;
        this.minute = minute;
        this.action = action;
        this.player = player;
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

    @Override
    public String toString() {
        return "GameAction{" +
                "id=" + id +
                ", minute=" + minute +
                ", action=" + action +
                ", player=" + player +
                '}';
    }
}
package dev.maxischiavello.football_stats.substitution;

import dev.maxischiavello.football_stats.player.Player;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "substitutions")
public class Substitution {
    @Id
    @GeneratedValue
    private Integer id;
    private Player playerOut;
    private Player playerIn;
    private Integer minute;

    public Substitution() {}

    public Substitution(Integer id, Player playerOut, Player playerIn, Integer minute) {
        this.id = id;
        this.playerOut = playerOut;
        this.playerIn = playerIn;
        this.minute = minute;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayerOut() {
        return playerOut;
    }

    public void setPlayerOut(Player playerOut) {
        this.playerOut = playerOut;
    }

    public Player getPlayerIn() {
        return playerIn;
    }

    public void setPlayerIn(Player playerIn) {
        this.playerIn = playerIn;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "Substitution{" +
                "id=" + id +
                ", playerOut=" + playerOut +
                ", playerIn=" + playerIn +
                ", minute=" + minute +
                '}';
    }
}

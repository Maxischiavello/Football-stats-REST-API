package dev.maxischiavello.football_stats.substitution;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.maxischiavello.football_stats.player.Player;
import dev.maxischiavello.football_stats.result.Result;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "substitution")
public class Substitution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "player_out_id")
    @NotNull(message = "Player out must not be null")
    private Player playerOut;

    @ManyToOne
    @JoinColumn(name = "player_in_id")
    @NotNull(message = "Player in must not be null")
    private Player playerIn;

    @NotNull(message = "Minute must not be null")
    private Integer minute;

    @ManyToOne
    @JoinColumn(name = "result_id")
    @JsonBackReference
    private Result result;

    public Substitution() {
    }

    public Substitution(Integer id, Player playerOut, Player playerIn, Integer minute, Result result) {
        this.id = id;
        this.playerOut = playerOut;
        this.playerIn = playerIn;
        this.minute = minute;
        this.result = result;
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Substitution{" +
                "id=" + id +
                ", playerOut=" + playerOut +
                ", playerIn=" + playerIn +
                ", minute=" + minute +
                ", result=" + result +
                '}';
    }
}

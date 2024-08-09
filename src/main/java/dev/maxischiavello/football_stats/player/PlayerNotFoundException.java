package dev.maxischiavello.football_stats.player;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(Integer id) {
        super("Player not found with the id: " + id);
    }
}

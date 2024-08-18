package dev.maxischiavello.football_stats.game_actions;

public class GameActionNotFoundException extends RuntimeException {

    public GameActionNotFoundException(Integer id) {
        super("Game action not found with id: " + id);
    }
}

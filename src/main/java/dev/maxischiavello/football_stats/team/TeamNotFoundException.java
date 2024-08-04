package dev.maxischiavello.football_stats.team;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Integer id) {
        super("Team not found with id: " + id);
    }
}

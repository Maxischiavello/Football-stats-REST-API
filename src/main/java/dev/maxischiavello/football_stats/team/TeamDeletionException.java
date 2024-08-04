package dev.maxischiavello.football_stats.team;

public class TeamDeletionException extends RuntimeException {
    public TeamDeletionException(String message) {
        super(message);
    }

    public TeamDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}

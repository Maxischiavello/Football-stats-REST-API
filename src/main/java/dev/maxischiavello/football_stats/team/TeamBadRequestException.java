package dev.maxischiavello.football_stats.team;

public class TeamBadRequestException extends RuntimeException {
    public TeamBadRequestException(String message) {
        super(message);
    }

    public TeamBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

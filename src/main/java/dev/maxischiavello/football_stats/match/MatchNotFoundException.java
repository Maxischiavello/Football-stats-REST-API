package dev.maxischiavello.football_stats.match;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(Integer id) {
        super("Match not found with the id: " + id);
    }
}

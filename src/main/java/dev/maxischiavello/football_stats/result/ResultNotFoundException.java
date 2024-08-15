package dev.maxischiavello.football_stats.result;

public class ResultNotFoundException extends RuntimeException {

    public ResultNotFoundException(Integer id) {
        super("Result not found with id: " + id);
    }
}

package dev.maxischiavello.football_stats.substitution;

public class SubstitutionNotFoundException extends RuntimeException {
    public SubstitutionNotFoundException(Integer id) {
        super("Substitution not found with id: " + id);
    }
}

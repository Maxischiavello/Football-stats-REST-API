package dev.maxischiavello.football_stats.team;

import jakarta.validation.constraints.NotNull;

public record TeamStatsRequest(
        @NotNull(message = "Team points must not be null") Integer points,
        @NotNull(message = "Team goals scored must not be null") Integer goalsScored,
        @NotNull(message = "Team goals conceded must not be null") Integer goalsConceded
) {
}

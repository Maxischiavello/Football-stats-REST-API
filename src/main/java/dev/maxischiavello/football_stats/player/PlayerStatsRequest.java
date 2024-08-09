package dev.maxischiavello.football_stats.player;

import jakarta.validation.constraints.NotNull;

public record PlayerStatsRequest(
        @NotNull(message = "Player goals must not be null") Integer goals,
        @NotNull(message = "Player assists must not be null") Integer assists,
        @NotNull(message = "Player yellow cards must not be null") Integer yellowCards,
        @NotNull(message = "Player red cards must not be null") Integer redCards
) {
}

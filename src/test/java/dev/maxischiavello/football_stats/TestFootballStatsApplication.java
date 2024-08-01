package dev.maxischiavello.football_stats;

import org.springframework.boot.SpringApplication;

public class TestFootballStatsApplication {

	public static void main(String[] args) {
		SpringApplication.from(FootballStatsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

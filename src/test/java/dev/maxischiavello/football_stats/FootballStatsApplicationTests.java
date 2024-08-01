package dev.maxischiavello.football_stats;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class FootballStatsApplicationTests {

	@Test
	void contextLoads() {
	}

}

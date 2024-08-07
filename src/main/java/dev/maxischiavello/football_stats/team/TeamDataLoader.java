package dev.maxischiavello.football_stats.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class TeamDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(TeamDataLoader.class);
    private final ObjectMapper objectMapper;
    private final TeamRepository teamRepository;

    public TeamDataLoader(ObjectMapper objectMapper, TeamRepository teamRepository) {
        this.objectMapper = objectMapper;
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (teamRepository.count() == 0) {
            String TEAMS_JSON = "/data/teams.json";
            log.info("Loading teams into database from JSON: {}", TEAMS_JSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(TEAMS_JSON)) {
                Teams response = objectMapper.readValue(inputStream, Teams.class);
                teamRepository.saveAll(response.teams());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }
}

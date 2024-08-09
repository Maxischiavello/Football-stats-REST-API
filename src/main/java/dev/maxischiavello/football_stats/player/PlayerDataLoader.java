package dev.maxischiavello.football_stats.player;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Order(2)
public class PlayerDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(PlayerDataLoader.class);
    private final ObjectMapper objectMapper;
    private final PlayerRepository playerRepository;

    public PlayerDataLoader(ObjectMapper objectMapper, PlayerRepository playerRepository) {
        this.objectMapper = objectMapper;
        this.playerRepository = playerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (playerRepository.count() == 0) {
            String PLAYERS_JSON = "/data/players.json";
            log.info("Loading players into database from JSON: {}", PLAYERS_JSON);

            try (InputStream inputStream = TypeReference.class.getResourceAsStream(PLAYERS_JSON)) {
                Players response = objectMapper.readValue(inputStream, Players.class);
                playerRepository.saveAll(response.players());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }
}

package dev.maxischiavello.football_stats.game_actions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.maxischiavello.football_stats.player.Players;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Order(5)
public class GameActionDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(GameActionDataLoader.class);
    private final ObjectMapper objectMapper;
    private final GameActionRepository gameActionRepository;

    public GameActionDataLoader(ObjectMapper objectMapper, GameActionRepository gameActionRepository) {
        this.objectMapper = objectMapper;
        this.gameActionRepository = gameActionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (gameActionRepository.count() == 0) {
            String GAME_ACTIONS_JSON = "/data/game_actions.json";
            log.info("Loading game actions into database from JSON: {}", GAME_ACTIONS_JSON);

            try (InputStream inputStream = TypeReference.class.getResourceAsStream(GAME_ACTIONS_JSON)) {
                GameActions response = objectMapper.readValue(inputStream, GameActions.class);
                gameActionRepository.saveAll(response.gameActions());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }
}

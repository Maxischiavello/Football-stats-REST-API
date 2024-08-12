package dev.maxischiavello.football_stats.match;

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
@Order(3)
public class MatchDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MatchDataLoader.class);
    private final ObjectMapper objectMapper;

    private final MatchRepository matchRepository;

    public MatchDataLoader(ObjectMapper objectMapper, MatchRepository matchRepository) {
        this.objectMapper = objectMapper;
        this.matchRepository = matchRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (matchRepository.count() == 0) {
            String MATCH_JSON = "/data/matches.json";
            log.info("Loading matches into database from JSON: {}", MATCH_JSON);

            try (InputStream inputStream = TypeReference.class.getResourceAsStream(MATCH_JSON)) {
                Matches response = objectMapper.readValue(inputStream, Matches.class);
                matchRepository.saveAll(response.matches());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }
}

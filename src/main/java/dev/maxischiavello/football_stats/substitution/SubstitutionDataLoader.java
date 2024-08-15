package dev.maxischiavello.football_stats.substitution;

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
@Order(4)
public class SubstitutionDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SubstitutionDataLoader.class);
    private final ObjectMapper objectMapper;
    private final SubstitutionRepository substitutionRepository;

    public SubstitutionDataLoader(ObjectMapper objectMapper, SubstitutionRepository substitutionRepository) {
        this.objectMapper = objectMapper;
        this.substitutionRepository = substitutionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (substitutionRepository.count() == 0) {
            String SUBSTITUTIONS_JSON = "/data/substitutions.json";
            log.info("Loading substitutions into database from JSON: {}", SUBSTITUTIONS_JSON);

            try (InputStream inputStream = TypeReference.class.getResourceAsStream(SUBSTITUTIONS_JSON)) {
                Substitutions response = objectMapper.readValue(inputStream, Substitutions.class);
                substitutionRepository.saveAll(response.substitutions());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }
}

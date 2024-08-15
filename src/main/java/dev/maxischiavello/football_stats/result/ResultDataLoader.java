package dev.maxischiavello.football_stats.result;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.maxischiavello.football_stats.substitution.SubstitutionDataLoader;
import dev.maxischiavello.football_stats.substitution.Substitutions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Order(5)
public class ResultDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ResultDataLoader.class);
    private final ObjectMapper objectMapper;
    private final ResultRepository resultRepository;

    public ResultDataLoader(ObjectMapper objectMapper, ResultRepository resultRepository) {
        this.objectMapper = objectMapper;
        this.resultRepository = resultRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (resultRepository.count() == 0) {
            String RESULTS_JSON = "/data/results.json";
            log.info("Loading results into database from JSON: {}", RESULTS_JSON);

            try (InputStream inputStream = TypeReference.class.getResourceAsStream(RESULTS_JSON)) {
                Results response = objectMapper.readValue(inputStream, Results.class);
                resultRepository.saveAll(response.results());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }
}

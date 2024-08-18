package dev.maxischiavello.football_stats.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.maxischiavello.football_stats.match.Match;
import dev.maxischiavello.football_stats.match.MatchNotFoundException;
import dev.maxischiavello.football_stats.match.MatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
@Order(4)
public class ResultDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ResultDataLoader.class);
    private final ObjectMapper objectMapper;
    private final ResultRepository resultRepository;
    private final MatchRepository matchRepository;

    public ResultDataLoader(ObjectMapper objectMapper, ResultRepository resultRepository, MatchRepository matchRepository) {
        this.objectMapper = objectMapper;
        this.resultRepository = resultRepository;
        this.matchRepository = matchRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (resultRepository.count() == 0) {
            String RESULTS_JSON = "/data/results.json";
            log.info("Loading results into database from JSON: {}", RESULTS_JSON);

            try (InputStream inputStream = TypeReference.class.getResourceAsStream(RESULTS_JSON)) {
                Results response = objectMapper.readValue(inputStream, Results.class);
                resultRepository.saveAll(response.results());

                Match match1 = matchRepository.findById(1).orElseThrow(() -> new MatchNotFoundException(1));
                match1.setResult(resultRepository.findById(1).orElseThrow(() -> new ResultNotFoundException(1)));
                matchRepository.save(match1);

                Match match2 = matchRepository.findById(2).orElseThrow(() -> new MatchNotFoundException(2));
                match2.setResult(resultRepository.findById(2).orElseThrow(() -> new ResultNotFoundException(2)));
                matchRepository.save(match2);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }
}

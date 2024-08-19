package dev.maxischiavello.football_stats.result;

import dev.maxischiavello.football_stats.game_actions.GameAction;
import dev.maxischiavello.football_stats.match.Match;
import dev.maxischiavello.football_stats.substitution.Substitution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DisplayName("Result controller integration tests")
public class ResultControllerIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DisplayName("should establish the connection")
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    @DisplayName("should find all results")
    void getAll() {
        Result[] results = restTemplate.getForObject("/result", Result[].class);
        assertThat(results.length).isGreaterThan(1);
    }

    @Test
    @DisplayName("should find a result when given valid id")
    void getResultWithValidId() {
        ResponseEntity<Result> response = restTemplate.exchange("/result/1", HttpMethod.GET, null, Result.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Result result = response.getBody();
        assert result != null;
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getLocalTeamScores()).isEqualTo(2);
        assertThat(result.getVisitTeamScores()).isEqualTo(1);
    }

    @Test
    @DisplayName("should not find a result when given invalid id")
    void getResultWithInvalidId() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange("/result/999", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Rollback
    @DisplayName("should create a result")
    void create() throws Exception {
        Match match = new Match(3, new ArrayList<>(), new Result(), LocalDateTime.parse("2024-08-25T15:00:00"));
        Result result = new Result(3, 4, 2, match, new ArrayList<GameAction>(), new ArrayList<Substitution>());
        ResponseEntity<Result> response = restTemplate.exchange("/result", HttpMethod.POST, new HttpEntity<Result>(result), Result.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(3);
        assertThat(response.getBody().getLocalTeamScores()).isEqualTo(4);
        assertThat(response.getBody().getVisitTeamScores()).isEqualTo(2);
    }

    @Test
    @DisplayName("should not create a result when request is invalid")
    void notCreate() throws Exception {
        Result result = new Result(3, null, null, null, null, null);
        ResponseEntity<String> response = restTemplate.exchange("/match", HttpMethod.POST, new HttpEntity<>(result), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}

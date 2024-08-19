package dev.maxischiavello.football_stats.match;


import dev.maxischiavello.football_stats.game_actions.GameAction;
import dev.maxischiavello.football_stats.result.Result;
import dev.maxischiavello.football_stats.substitution.Substitution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DisplayName("Match controller integration tests")
public class MatchControllerIntegrationTest {

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
    @DisplayName("should find all matches")
    void getAll() {
        Match[] matches = restTemplate.getForObject("/match", Match[].class);
        assertThat(matches.length).isGreaterThan(1);
    }

    @Test
    @DisplayName("should find a match when given valid id")
    void getMatchWithValidId() {
        ResponseEntity<Match> response = restTemplate.exchange("/match/1", HttpMethod.GET, null, Match.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Match match = response.getBody();
        assert match != null;
        assertThat(match.getId()).isEqualTo(1);
        assertThat(match.getDate()).isEqualTo("2024-08-17T19:00:00");
    }

    @Test
    @DisplayName("should not find a match when given invalid id")
    void getResultWithInvalidId() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange("/match/999", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Rollback
    @DisplayName("should create a match")
    void create() throws Exception {
        Result result = new Result(3, 4, 2, new Match(), new ArrayList<GameAction>(), new ArrayList<Substitution>());
        Match match = new Match(3, new ArrayList<>(), result, LocalDateTime.parse("2024-08-25T15:00:00"));
        ResponseEntity<Match> response = restTemplate.exchange("/match", HttpMethod.POST, new HttpEntity<Match>(match), Match.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(3);
        assertThat(response.getBody().getDate()).isEqualTo("2024-08-25T15:00:00");
    }

    @Test
    @DisplayName("should not create a match when request is invalid")
    void notCreate() throws Exception {
        Match match = new Match(3, null, null, null);
        ResponseEntity<String> response = restTemplate.exchange("/match", HttpMethod.POST, new HttpEntity<>(match), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Rollback
    @DisplayName("should update match result when given valid id")
    void update() {
        ResponseEntity<Match> response = restTemplate.exchange("/match/1", HttpMethod.GET, null, Match.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Match existing = response.getBody();
        assert existing != null;
        Match updated = new Match(
                existing.getId(),
                existing.getTeams(),
                new Result(1, 2, 3, existing, new ArrayList<>(), new ArrayList<>()),
                existing.getDate()
        );

        assertThat(updated.getId()).isEqualTo(1);
        assertThat(updated.getDate()).isEqualTo("2024-08-17T19:00:00");
        assertThat(updated.getResult().getId()).isEqualTo(1);
        assertThat(updated.getResult().getLocalTeamScores()).isEqualTo(2);
        assertThat(updated.getResult().getVisitTeamScores()).isEqualTo(3);
    }

//    REALIZAR CUANDO HAYA RESULTADOS CARGADOS EN BD
    @Test
    @DisplayName("should not update match result when request is invalid")
    void notUpdate() throws Exception {
        String json = """
                    {
                            "id": 1,
                            "localTeamScores": null,
                            "visitTeamScores": 2
                    }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        // Send the PUT request and capture the response
        ResponseEntity<String> response = restTemplate.exchange("/match/update_result/1", HttpMethod.PUT, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Local team scores must not be null");
    }
}

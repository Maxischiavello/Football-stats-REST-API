package dev.maxischiavello.football_stats.substitution;

import dev.maxischiavello.football_stats.game_actions.Action;
import dev.maxischiavello.football_stats.game_actions.GameAction;
import dev.maxischiavello.football_stats.match.Match;
import dev.maxischiavello.football_stats.player.Player;
import dev.maxischiavello.football_stats.result.Result;
import dev.maxischiavello.football_stats.team.Team;
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

import java.util.ArrayList;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DisplayName("Substitution controller integration tests")
public class SubstitutionControllerIntegrationTest {

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
    @DisplayName("should find all substitution")
    void getAll() {
        Substitution[] substitutions = restTemplate.getForObject("/substitution", Substitution[].class);
        assertThat(substitutions.length).isGreaterThan(1);
    }

    @Test
    @DisplayName("should find a substitution when given valid id")
    void getSubstitutionWithValidId() {
        ResponseEntity<Substitution> response = restTemplate.exchange("/substitution/1", HttpMethod.GET, null, Substitution.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Substitution substitution = response.getBody();
        assert substitution != null;
        assertThat(substitution.getId()).isEqualTo(1);
        assertThat(substitution.getMinute()).isEqualTo(75);
    }

    @Test
    @DisplayName("should not find a substitution when given invalid id")
    void getSubstitutionWithInvalidId() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange("/substitution/999", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Rollback
    @DisplayName("should create a substitution")
    void create() throws Exception {
        Player player = new Player(3, "Test", "Player", 40, new Team(), 0, 0, 0, 0);
        Player player2 = new Player(4, "Test2", "Player2", 40, new Team(), 0, 0, 0, 0);
        Result result = new Result(3, 4, 2, new Match(), new ArrayList<GameAction>(), new ArrayList<Substitution>());

        Substitution substitution = new Substitution(5, player, player2, 80, result);
        ResponseEntity<Substitution> response = restTemplate.exchange("/substitution", HttpMethod.POST, new HttpEntity<Substitution>(substitution), Substitution.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(5);
        assertThat(response.getBody().getMinute()).isEqualTo(80);
    }

    @Test
    @DisplayName("should not create a substitution when request is invalid")
    void notCreate() throws Exception {
        Substitution substitution = new Substitution(5, null, null, null, null);
        ResponseEntity<String> response = restTemplate.exchange("/substitution", HttpMethod.POST, new HttpEntity<>(substitution), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}

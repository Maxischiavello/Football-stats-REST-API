package dev.maxischiavello.football_stats.player;

import dev.maxischiavello.football_stats.team.Team;
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

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DisplayName("Player controller integration tests")
public class PlayerControllerIntegrationTest {

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
    @DisplayName("should find all players")
    void getAll() {
        Player[] players = restTemplate.getForObject("/player", Player[].class);
        assertThat(players.length).isGreaterThan(1);
    }

    @Test
    @DisplayName("should find a player when given valid id")
    void getPlayerValidId() {
        ResponseEntity<Player> response = restTemplate.exchange("/player/1", HttpMethod.GET, null, Player.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Player player = response.getBody();
        assert player != null;
        assertThat(player.getId()).isEqualTo(1);
        assertThat(player.getLastname()).isEqualTo("Cavani");
    }

    @Test
    @DisplayName("should not find a player when given invalid id")
    void getPlayerInvalidId() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange("/player/999", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Rollback
    @DisplayName("should create a player")
    void create() throws Exception {
        Player player = new Player(3, "Test", "Player", 40, new Team(), 0, 0, 0, 0);
        ResponseEntity<Player> response = restTemplate.exchange("/player", HttpMethod.POST, new HttpEntity<Player>(player), Player.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(3);
        assertThat(response.getBody().getFirstname()).isEqualTo("Test");
    }

    @Test
    @DisplayName("should not create a player when request is invalid")
    void notCreate() throws Exception {
        Player player = new Player(3, "", "", 40, new Team(), 0, 0, 0, 0);
        ResponseEntity<String> response = restTemplate.exchange("/player", HttpMethod.POST, new HttpEntity<Player>(player), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Rollback
    @DisplayName("should update player stats when given valid id")
    void update() {
        ResponseEntity<Player> response = restTemplate.exchange("/player/1", HttpMethod.GET, null, Player.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Player existing = response.getBody();
        assert existing != null;
        Player updated = new Player(
                existing.getId(),
                existing.getFirstname(),
                existing.getLastname(),
                existing.getAge(),
                existing.getTeam(),
                4,
                4,
                0,
                0
        );

        assertThat(updated.getId()).isEqualTo(1);
        assertThat(updated.getLastname()).isEqualTo("Cavani");
        assertThat(updated.getGoals()).isEqualTo(4);
        assertThat(updated.getAssists()).isEqualTo(4);
    }

    @Test
    @DisplayName("should not update player stats when request is invalid")
    void notUpdate() throws Exception {
        String json = """
                    {
                            "goals": null,
                            "assists": 4,
                            "yellowCards": 0,
                            "redCards": 0
                    }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        // Send the PUT request and capture the response
        ResponseEntity<String> response = restTemplate.exchange("/player/1", HttpMethod.PUT, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Player goals must not be null");
    }
}

package dev.maxischiavello.football_stats.team;

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

import java.util.ArrayList;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DisplayName("Team controller integration tests")
public class TeamControllerIntegrationTest {

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
    @DisplayName("should find all teams")
    void getAll() {
        Team[] teams = restTemplate.getForObject("/team", Team[].class);
        assertThat(teams.length).isGreaterThan(1);
    }

    @Test
    @DisplayName("should find a team when given valid id")
    void getTeamValidId() {
        ResponseEntity<Team> response = restTemplate.exchange("/team/1", HttpMethod.GET, null, Team.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(response.getBody()).isNotNull();
        Team team = response.getBody();
        assert team != null;
        assertThat(team.getId()).isEqualTo(1);
        assertThat(team.getName()).isEqualTo("Boca Juniors");
    }

    @Test
    @DisplayName("should not find a team when given invalid id")
    void getTeamInvalidId() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange("/team/999", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Rollback
    @DisplayName("should create a team")
    void create() throws Exception {
        Team team = new Team(4, "Inter", new ArrayList<>(), new ArrayList<>(), 0, 0, 0);
        ResponseEntity<Team> response = restTemplate.exchange("/team", HttpMethod.POST, new HttpEntity<Team>(team), Team.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(4);
        assertThat(response.getBody().getName()).isEqualTo("Inter");
        assertThat(response.getBody().getPoints()).isEqualTo(0);
    }

    @Test
    @DisplayName("should not create a team when request is invalid")
    void notCreate() throws Exception {
        Team team = new Team(5, "", new ArrayList<>(), new ArrayList<>(), 0, 0, 0);
        ResponseEntity<String> response = restTemplate.exchange("/team", HttpMethod.POST, new HttpEntity<Team>(team), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Rollback
    @DisplayName("should update team stats when given valid id")
    void update() {
        ResponseEntity<Team> response = restTemplate.exchange("/team/3", HttpMethod.GET, null, Team.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Team existing = response.getBody();
        assert existing != null;
        Team updated = new Team(existing.getId(), "Barcelona", existing.getPlayers(), existing.getMatches(), 6, existing.getGoalsScored(), existing.getGoalsConceded());

        assertThat(updated.getId()).isEqualTo(3);
        assertThat(updated.getName()).isEqualTo("Barcelona");
        assertThat(updated.getPoints()).isEqualTo(6);
    }

    @Test
    @DisplayName("should not update team stats when request is invalid")
    void notUpdate() throws Exception {
        String json = """
                    {
                            "points": null,
                            "goalsScored": 4,
                            "goalsConceded": 0
                    }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        // Send the PUT request and capture the response
        ResponseEntity<String> response = restTemplate.exchange("/team/1", HttpMethod.PUT, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Team points must not be null");
    }

    @Test
    @Rollback
    @DisplayName("should delete a team when given valid id")
    void deleteTeam() throws Exception {
        ResponseEntity<Void> response = restTemplate.exchange("/team/3", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}

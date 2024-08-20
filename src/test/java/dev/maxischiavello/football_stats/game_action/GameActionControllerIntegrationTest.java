package dev.maxischiavello.football_stats.game_action;

import dev.maxischiavello.football_stats.game_actions.Action;
import dev.maxischiavello.football_stats.game_actions.GameAction;
import dev.maxischiavello.football_stats.match.Match;
import dev.maxischiavello.football_stats.player.Player;
import dev.maxischiavello.football_stats.result.Result;
import dev.maxischiavello.football_stats.substitution.Substitution;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DisplayName("Game action controller integration tests")
public class GameActionControllerIntegrationTest {

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
    @DisplayName("should find all game actions")
    void getAll() {
        GameAction[] gameActions = restTemplate.getForObject("/game_action", GameAction[].class);
        assertThat(gameActions.length).isGreaterThan(1);
    }

    @Test
    @DisplayName("should find a game action when given valid id")
    void getGameActionWithValidId() {
        ResponseEntity<GameAction> response = restTemplate.exchange("/game_action/1", HttpMethod.GET, null, GameAction.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        GameAction gameAction = response.getBody();
        assert gameAction != null;
        assertThat(gameAction.getId()).isEqualTo(1);
        assertThat(gameAction.getMinute()).isEqualTo(35);
        assertThat(gameAction.getAction()).isEqualTo(Action.GOAL);
    }

    @Test
    @DisplayName("should not find a game action when given invalid id")
    void getGameActionWithInvalidId() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange("/game_action/999", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Rollback
    @DisplayName("should create a game action")
    void create() throws Exception {
        Player player = new Player(3, "Test", "Player", 40, new Team(), 0, 0, 0, 0);
        Result result = new Result(3, 4, 2, new Match(), new ArrayList<GameAction>(), new ArrayList<Substitution>());
        GameAction gameAction = new GameAction(7,40,Action.GOAL,player,result);
        ResponseEntity<GameAction> response = restTemplate.exchange("/game_action", HttpMethod.POST, new HttpEntity<GameAction>(gameAction), GameAction.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(7);
        assertThat(response.getBody().getMinute()).isEqualTo(40);
        assertThat(response.getBody().getAction()).isEqualTo(Action.GOAL);
    }

    @Test
    @DisplayName("should not create a game action when request is invalid")
    void notCreate() throws Exception {
        GameAction gameAction = new GameAction(7,null, null, null, null);
        ResponseEntity<String> response = restTemplate.exchange("/game_action", HttpMethod.POST, new HttpEntity<>(gameAction), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}

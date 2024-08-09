package dev.maxischiavello.football_stats.team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static java.lang.StringTemplate.STR;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = {TeamController.class, TeamExceptionHandler.class})
@AutoConfigureMockMvc
public class TeamControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TeamService teamService;

    List<Team> teams = new ArrayList<>();

    @BeforeEach
    void setUp() {
        teams = List.of(
                new Team(1, "Boca Juniors", new ArrayList<>(), new ArrayList<>(), 3, 5, 1),
                new Team(2, "Real Madrid", new ArrayList<>(), new ArrayList<>(), 0, 1, 5)
        );
    }

    @Test
    @DisplayName("should find all teams")
    void getAll() throws Exception {
        String jsonResponse = """
                    [
                        {
                            "id": 1,
                            "name": "Boca Juniors",
                            "points": 3,
                            "goalsScored": 5,
                            "goalsConceded": 1,
                            "players": [],
                            "matches": []
                        },
                        {
                            "id": 2,
                            "name": "Real Madrid",
                            "points": 0,
                            "goalsScored": 1,
                            "goalsConceded": 5,
                            "players": [],
                            "matches": []
                        }
                    ]
                """;

        when(teamService.getAll()).thenReturn(teams);

        mockMvc.perform(get("/team"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    @DisplayName("should find a team when given valid id")
    void getTeamValidId() throws Exception {
        when(teamService.getTeam(1)).thenReturn(teams.get(0));

        var team = teams.get(0);
        var json = STR. """
                    {
                            "id": \{ team.getId() },
                            "name": "\{ team.getName() }",
                            "points": \{ team.getPoints() },
                            "goalsScored": \{ team.getGoalsScored() },
                            "goalsConceded": \{ team.getGoalsConceded() },
                            "players": [],
                            "matches": []
                    }
                """ ;

        mockMvc.perform(get("/team/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    @DisplayName("should not find a team when given invalid id")
    void getTeamInvalidId() throws Exception {
        when(teamService.getTeam(999)).thenThrow(new TeamNotFoundException(999));

        mockMvc.perform(get("/team/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Team not found with id: 999"));
    }

    @Test
    @DisplayName("should create a team")
    void create() throws Exception {
        Team team = new Team(3, "Liverpool", new ArrayList<>(), new ArrayList<>(), 6, 4, 2);
        when(teamService.create(any(Team.class))).thenReturn(team);
        String json = STR. """
                    {
                            "id": \{ team.getId() },
                            "name": "\{ team.getName() }",
                            "points": \{ team.getPoints() },
                            "goalsScored": \{ team.getGoalsScored() },
                            "goalsConceded": \{ team.getGoalsConceded() },
                            "players": [],
                            "matches": []
                    }
                """ ;

        mockMvc.perform(post("/team")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("should not create a team when request is invalid")
    void notCreate() throws Exception {
        Team team = new Team(3, " ", new ArrayList<>(), new ArrayList<>(), 6, 4, 2);
        String json = STR. """
                    {
                            "id": \{ team.getId() },
                            "name": "\{ team.getName() }",
                            "points": \{ team.getPoints() },
                            "goalsScored": \{ team.getGoalsScored() },
                            "goalsConceded": \{ team.getGoalsConceded() },
                            "players": [],
                            "matches": []
                    }
                """ ;

        mockMvc.perform(post("/team")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Team name must not be empty"));
    }

    @Test
    @DisplayName("should update team stats when given valid id")
    void update() throws Exception {
        Team updated = teams.get(0);
        TeamStatsRequest teamStatsRequest = new TeamStatsRequest(3, 4, 0);
        when(teamService.updateStats(anyInt(), any(TeamStatsRequest.class))).thenReturn(updated);
        String json = STR. """
                    {
                            "points": \{ teamStatsRequest.points() },
                            "goalsScored": \{ teamStatsRequest.goalsScored() },
                            "goalsConceded": \{ teamStatsRequest.goalsConceded() }
                    }
                """ ;

        mockMvc.perform(put("/team/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should not update team stats when request is invalid")
    void notUpdate() throws Exception {
        TeamStatsRequest teamStatsRequest = new TeamStatsRequest(null, 4, 0);
        String json = STR. """
                    {
                            "points": \{ teamStatsRequest.points() },
                            "goalsScored": \{ teamStatsRequest.goalsScored() },
                            "goalsConceded": \{ teamStatsRequest.goalsConceded() }
                    }
                """ ;

        mockMvc.perform(put("/team/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Team points must not be null"));
    }

    @Test
    @DisplayName("should delete a team when given valid id")
    void deleteTeam() throws Exception {
        doNothing().when(teamService).deleteTeam(1);

        mockMvc.perform(delete("/team/1"))
                .andExpect(status().isNoContent());

        verify(teamService, times(1)).deleteTeam(1);
    }

}

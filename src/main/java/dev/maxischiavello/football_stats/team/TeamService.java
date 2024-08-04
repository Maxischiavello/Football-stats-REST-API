package dev.maxischiavello.football_stats.team;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    List<Team> getAll() {
        return this.teamRepository.findAll();
    }

    Team getTeam(Integer id) {
        return this.teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }

    Team create(Team team) {
        if (team.getName() == null || team.getName().isEmpty()) {
            throw new TeamBadRequestException("Team name must not be empty");
        }
        return this.teamRepository.save(team);
    }

    Team updateTeamStats(Integer id, TeamRequest teamRequest) {
        Team updated = this.teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));

        updated.setPoints(teamRequest.points());
        updated.setGoalsScored(teamRequest.goalsScored());
        updated.setGoalsConceded(teamRequest.goalsConceded());

        return updated;
    }

    public void deleteTeam(Integer id) {
        try {
            teamRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new TeamNotFoundException(id);
        } catch (Exception e) {
            throw new TeamDeletionException("Failed to delete team with id: " + id, e);
        }
    }
}

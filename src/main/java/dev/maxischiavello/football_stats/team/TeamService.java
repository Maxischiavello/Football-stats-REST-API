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
        return this.teamRepository.save(team);
    }

    Team updateStats(Integer id, TeamStatsRequest teamStatsRequest) {
        Team updated = this.teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));

        updated.setPoints(updated.getPoints() + teamStatsRequest.points());
        updated.setGoalsScored(updated.getGoalsScored() + teamStatsRequest.goalsScored());
        updated.setGoalsConceded(updated.getGoalsConceded() + teamStatsRequest.goalsConceded());

        return updated;
    }

    public void deleteTeam(Integer id) {
        try {
            teamRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new TeamNotFoundException(id);
        }
    }
}

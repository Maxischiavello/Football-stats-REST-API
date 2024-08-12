package dev.maxischiavello.football_stats.player;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    List<Player> getAll() {
        return this.playerRepository.findAll();
    }

    Player getPlayer(Integer id) {
        return this.playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
    }

    Player create(Player player) {
        return this.playerRepository.save(player);
    }

    Player updatePlayerStats(Integer id, PlayerStatsRequest playerStatsRequest) {
        Player updated = this.playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));

        updated.setGoals(updated.getGoals() + playerStatsRequest.goals());
        updated.setAssists(updated.getAssists() + playerStatsRequest.assists());
        updated.setYellowCards(updated.getYellowCards() + playerStatsRequest.yellowCards());
        updated.setRedCards(updated.getRedCards() + playerStatsRequest.redCards());

        return updated;
    }

    public void deletePlayer(Integer id) {
        try {
            playerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new PlayerNotFoundException(id);
        }
    }
}

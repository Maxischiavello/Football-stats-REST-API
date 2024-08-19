package dev.maxischiavello.football_stats.game_actions;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameActionService {

    private final GameActionRepository gameActionRepository;

    public GameActionService(GameActionRepository gameActionRepository) {
        this.gameActionRepository = gameActionRepository;
    }

    List<GameAction> getAll() {
        return gameActionRepository.findAll();
    }

    GameAction getGameAction(Integer id) {
        return gameActionRepository.findById(id).orElseThrow(() -> new GameActionNotFoundException(id));
    }

    GameAction create(GameAction gameAction) {
        return gameActionRepository.save(gameAction);
    }
}

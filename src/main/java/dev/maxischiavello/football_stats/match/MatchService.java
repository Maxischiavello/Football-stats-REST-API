package dev.maxischiavello.football_stats.match;

import dev.maxischiavello.football_stats.result.Result;
import dev.maxischiavello.football_stats.substitution.Substitution;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    List<Match> getAll() {
        return this.matchRepository.findAll();
    }

    Match getMatch(Integer id) {
        return this.matchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException(id));
    }

    Match create(Match match) {
        return this.matchRepository.save(match);
    }

    Match setResult(Integer matchId, Result result) {
        Match match = this.matchRepository.findById(matchId).orElseThrow(() -> new MatchNotFoundException(matchId));
        match.setResult(result);
        return match;
    }

    public void deleteMatch(Integer id) {
        try {
            matchRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new MatchNotFoundException(id);
        }
    }
}

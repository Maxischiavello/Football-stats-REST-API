package dev.maxischiavello.football_stats.result;

import dev.maxischiavello.football_stats.substitution.Substitution;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    List<Result> getAll() {
        return this.resultRepository.findAll();
    }

    Result getResult(Integer id) {
        return this.resultRepository.findById(id).orElseThrow(() -> new ResultNotFoundException(id));
    }

    Result create(Result result) {
        return this.resultRepository.save(result);
    }

    Result setSubstitutions(Integer resultId, List<Substitution> substitutions) {
        Result result = this.resultRepository.findById(resultId).orElseThrow(() -> new ResultNotFoundException(resultId));
        result.setSubstitutions(substitutions);
        return result;
    }
}

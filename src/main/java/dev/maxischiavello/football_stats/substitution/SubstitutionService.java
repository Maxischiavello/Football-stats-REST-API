package dev.maxischiavello.football_stats.substitution;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubstitutionService {

    private final SubstitutionRepository substitutionRepository;

    public SubstitutionService(SubstitutionRepository substitutionRepository) {
        this.substitutionRepository = substitutionRepository;
    }

    List<Substitution> getAll() {
        return substitutionRepository.findAll();
    }

    Substitution getSubstitution(Integer id) {
        return substitutionRepository.findById(id).orElseThrow(() -> new SubstitutionNotFoundException(id));
    }

    Substitution create(Substitution substitution) {
        return substitutionRepository.save(substitution);
    }
}



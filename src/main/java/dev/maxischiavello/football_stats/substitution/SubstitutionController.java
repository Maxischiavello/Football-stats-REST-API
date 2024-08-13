package dev.maxischiavello.football_stats.substitution;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/substitution")
public class SubstitutionController {

    public final SubstitutionService substitutionService;

    public SubstitutionController(SubstitutionService substitutionService) {
        this.substitutionService = substitutionService;
    }

    @GetMapping
    public ResponseEntity<List<Substitution>> getAll() {
        return ResponseEntity.ok(substitutionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Substitution> getSubstitution(@PathVariable Integer id) {
        return ResponseEntity.ok(substitutionService.getSubstitution(id));
    }

    @PostMapping
    public ResponseEntity<Substitution> create(@RequestBody @Valid Substitution substitution) {
        Substitution created = substitutionService.create(substitution);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri());
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }
}

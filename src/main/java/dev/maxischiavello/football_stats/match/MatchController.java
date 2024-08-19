package dev.maxischiavello.football_stats.match;

import dev.maxischiavello.football_stats.result.Result;
import dev.maxischiavello.football_stats.substitution.Substitution;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAll() {
        return ResponseEntity.ok(matchService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> get(@PathVariable Integer id) {
        return ResponseEntity.ok(matchService.getMatch(id));
    }

    @PostMapping
    public ResponseEntity<Match> create(@RequestBody @Valid Match match) {
        Match created = matchService.create(match);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri());
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @PutMapping("/update_result/{matchId}")
    public ResponseEntity<Match> updateMatchResult(@PathVariable Integer matchId, @RequestBody @Valid Result result) {
        Match updated = matchService.setResult(matchId, result);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{matchId}")
                .buildAndExpand(matchId)
                .toUri());
        return new ResponseEntity<>(updated, headers, HttpStatus.OK);
    }
}

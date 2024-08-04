package dev.maxischiavello.football_stats.team;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAll() {
        return ResponseEntity.ok(teamService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> get(@PathVariable Integer id) {
        return ResponseEntity.ok(teamService.getTeam(id));
    }

    @PostMapping("/teams")
    public ResponseEntity<Team> create(@RequestBody Team team) {
        Team created = teamService.create(team);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri());
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateStats(@PathVariable Integer id, @RequestBody TeamRequest teamRequest) {
        Team updated = teamService.updateTeamStats(id, teamRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri());
        return new ResponseEntity<>(updated, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}

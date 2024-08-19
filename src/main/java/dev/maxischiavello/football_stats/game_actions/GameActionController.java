package dev.maxischiavello.football_stats.game_actions;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/game_action")
public class GameActionController {

    private final GameActionService gameActionService;

    @Autowired
    public GameActionController(GameActionService gameActionService) {
        this.gameActionService = gameActionService;
    }

    @GetMapping
    public ResponseEntity<List<GameAction>> getAll() {
        return ResponseEntity.ok(gameActionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameAction> getGameAction(@PathVariable Integer id) {
        return ResponseEntity.ok(gameActionService.getGameAction(id));
    }

    @PostMapping
    public ResponseEntity<GameAction> create(@RequestBody @Valid GameAction gameAction) {
        GameAction created = gameActionService.create(gameAction);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri());
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }
}

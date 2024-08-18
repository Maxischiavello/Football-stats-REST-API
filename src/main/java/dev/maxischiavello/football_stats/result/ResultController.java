package dev.maxischiavello.football_stats.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/result")
public class ResultController {

    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<List<Result>> getAll() {
        return ResponseEntity.ok(resultService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> get(@PathVariable Integer id) {
        return ResponseEntity.ok(resultService.getResult(id));
    }

    @PostMapping
    public ResponseEntity<Result> create(@RequestBody Result result) {
        Result created = resultService.create(result);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri());
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }
}

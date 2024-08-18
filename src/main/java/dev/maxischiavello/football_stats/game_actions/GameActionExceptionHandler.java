package dev.maxischiavello.football_stats.game_actions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GameActionExceptionHandler {

    @ExceptionHandler(GameActionNotFoundException.class)
    public ResponseEntity<String> handleTeamNotFoundException(GameActionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

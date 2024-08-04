package dev.maxischiavello.football_stats.team;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TeamExceptionHandler {

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<String> handleTeamNotFoundException(TeamNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TeamBadRequestException.class)
    public ResponseEntity<String> handleTeamBadRequestException(TeamBadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TeamDeletionException.class)
    public ResponseEntity<String> handleTeamDeletionException(TeamDeletionException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}

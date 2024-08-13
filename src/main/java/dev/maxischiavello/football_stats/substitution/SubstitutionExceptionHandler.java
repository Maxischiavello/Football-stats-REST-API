package dev.maxischiavello.football_stats.substitution;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SubstitutionExceptionHandler {
    @ExceptionHandler(SubstitutionNotFoundException.class)
    public ResponseEntity<String> handleTeamNotFoundException(SubstitutionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

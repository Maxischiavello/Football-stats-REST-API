package dev.maxischiavello.football_stats.result;

import dev.maxischiavello.football_stats.substitution.SubstitutionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResultExceptionHandler {

    @ExceptionHandler(ResultNotFoundException.class)
    public ResponseEntity<String> handlePlayerNotFoundException(ResultNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

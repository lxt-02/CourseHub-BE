package course.learningservice.api.exception;

import course.learningservice.api.dto.response.ErrorResponse;
import course.learningservice.application.exception.ApplicationNotFoundException;
import course.learningservice.domain.model.learning.exception.LearningDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public record ApiExceptionHandler() {

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ApplicationNotFoundException exception) {
        return ErrorResponse.of("NOT_FOUND", exception.getMessage());
    }

    @ExceptionHandler({LearningDomainException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(RuntimeException exception) {
        return ErrorResponse.of("BAD_REQUEST", exception.getMessage());
    }
}

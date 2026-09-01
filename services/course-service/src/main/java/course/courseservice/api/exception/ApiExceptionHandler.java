package course.courseservice.api.exception;

import course.courseservice.api.dto.response.ErrorResponse;
import course.courseservice.application.exception.ApplicationConflictException;
import course.courseservice.application.exception.ApplicationNotFoundException;
import course.courseservice.domain.model.course.exception.CourseDomainException;
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

    @ExceptionHandler(ApplicationConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(ApplicationConflictException exception) {
        return ErrorResponse.of("CONFLICT", exception.getMessage());
    }

    @ExceptionHandler(CourseDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDomain(CourseDomainException exception) {
        return ErrorResponse.of("DOMAIN_ERROR", exception.getMessage());
    }
}

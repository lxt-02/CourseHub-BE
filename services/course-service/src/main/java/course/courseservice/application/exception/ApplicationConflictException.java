package course.courseservice.application.exception;

public class ApplicationConflictException extends RuntimeException {

    public ApplicationConflictException(String message) {
        super(message);
    }
}

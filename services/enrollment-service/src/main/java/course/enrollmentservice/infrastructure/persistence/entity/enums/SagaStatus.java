package course.enrollmentservice.infrastructure.persistence.entity.enums;

public enum SagaStatus {
    STARTED,
    PROCESSING,
    COMPLETED,
    COMPENSATING,
    COMPENSATED,
    FAILED
}

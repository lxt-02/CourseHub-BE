package course.learningservice.domain.model.learning.valueobject;

import course.learningservice.domain.model.learning.exception.LearningDomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record ProgressPercent(BigDecimal value) {

    public ProgressPercent {
        Objects.requireNonNull(value, "Progress percent must not be null");
        if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new LearningDomainException("Progress percent must be between 0 and 100");
        }
        value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public static ProgressPercent zero() {
        return new ProgressPercent(BigDecimal.ZERO);
    }
}

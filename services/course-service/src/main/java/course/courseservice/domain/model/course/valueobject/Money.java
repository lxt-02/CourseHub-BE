package course.courseservice.domain.model.course.valueobject;

import course.courseservice.domain.model.course.exception.CourseDomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal amount) implements Comparable<Money> {

    public Money {
        Objects.requireNonNull(amount, "Amount must not be null");
        amount = amount.setScale(2, RoundingMode.HALF_UP);

        if (amount.signum() < 0) {
            throw new CourseDomainException("Amount must not be negative");
        }
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    @Override
    public int compareTo(Money other) {
        return amount.compareTo(other.amount);
    }
}

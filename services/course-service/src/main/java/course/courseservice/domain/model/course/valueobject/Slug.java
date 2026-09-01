package course.courseservice.domain.model.course.valueobject;

import course.courseservice.domain.model.course.exception.CourseDomainException;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record Slug(String value) {

    private static final Pattern MARKS = Pattern.compile("\\p{M}+");
    private static final Pattern INVALID_CHARACTERS = Pattern.compile("[^a-z0-9]+");
    private static final Pattern EDGE_HYPHENS = Pattern.compile("(^-+|-+$)");
    private static final Pattern VALID_SLUG = Pattern.compile("^[a-z0-9]+(?:-[a-z0-9]+)*$");

    public Slug {
        Objects.requireNonNull(value, "Slug must not be null");
        value = value.trim().toLowerCase(Locale.ROOT);

        if (value.isBlank()) {
            throw new CourseDomainException("Slug must not be blank");
        }
        if (value.length() > 255) {
            throw new CourseDomainException("Slug must not exceed 255 characters");
        }
        if (!VALID_SLUG.matcher(value).matches()) {
            throw new CourseDomainException("Invalid slug: " + value);
        }
    }

    public static Slug fromTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new CourseDomainException("Course title must not be blank");
        }

        String normalized = Normalizer.normalize(
                title.trim().toLowerCase(Locale.ROOT),
                Normalizer.Form.NFD
        );
        normalized = MARKS.matcher(normalized).replaceAll("");
        normalized = normalized.replace('\u0111', 'd');
        normalized = INVALID_CHARACTERS.matcher(normalized).replaceAll("-");
        normalized = EDGE_HYPHENS.matcher(normalized).replaceAll("");

        return new Slug(normalized);
    }
}

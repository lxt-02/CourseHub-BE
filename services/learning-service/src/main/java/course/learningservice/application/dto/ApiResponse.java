package course.learningservice.application.dto;

import java.util.function.Function;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public <R> ApiResponse<R> map(Function<T, R> mapper) {
        return new ApiResponse<>(success, message, data == null ? null : mapper.apply(data));
    }
}

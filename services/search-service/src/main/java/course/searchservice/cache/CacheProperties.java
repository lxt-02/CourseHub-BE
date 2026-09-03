package course.searchservice.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.cache")
public class CacheProperties {
    private long ttlSeconds = 300;
    private long jitterRangeSeconds = 30;
    private String prefix = "search:";
}

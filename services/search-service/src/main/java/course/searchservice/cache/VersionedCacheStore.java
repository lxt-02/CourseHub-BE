package course.searchservice.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class VersionedCacheStore {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CacheProperties cacheProperties;

    public Object get(String domain, String rawQueryKey) {
        String cacheKey = buildCacheKey(domain, rawQueryKey);
        return redisTemplate.opsForValue().get(cacheKey);
    }

    public void put(String domain, String rawQueryKey, Object data) {
        String cacheKey = buildCacheKey(domain, rawQueryKey);
        long ttl = computeTtlWithJitter();
        redisTemplate.opsForValue().set(cacheKey, data, Duration.ofSeconds(ttl));
    }

    public void invalidateDomain(String domain) {
        String versionKey = cacheProperties.getPrefix() + domain + ":version";
        redisTemplate.opsForValue().increment(versionKey);
    }

    private String buildCacheKey(String domain, String rawQueryKey) {
        String versionKey = cacheProperties.getPrefix() + domain + ":version";
        Object versionObj = redisTemplate.opsForValue().get(versionKey);
        long version = versionObj != null ? Long.parseLong(versionObj.toString()) : 1L;

        String hashedKey = hashKey(rawQueryKey);
        return String.format("%s%s:v%d:%s", cacheProperties.getPrefix(), domain, version, hashedKey);
    }

    private long computeTtlWithJitter() {
        long baseTtl = cacheProperties.getTtlSeconds();
        long jitter = (long) (Math.random() * cacheProperties.getJitterRangeSeconds());
        return baseTtl + jitter;
    }

    private String hashKey(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.substring(0, 16);
        } catch (NoSuchAlgorithmException e) {
            return String.valueOf(input.hashCode());
        }
    }
}

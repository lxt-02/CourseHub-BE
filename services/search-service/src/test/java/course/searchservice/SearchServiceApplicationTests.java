package course.searchservice;

import course.searchservice.cache.VersionedCacheStore;
import course.searchservice.category.repository.CategoryElasticRepository;
import course.searchservice.course.repository.CourseElasticRepository;
import course.searchservice.course.repository.CourseSearchCustomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=" +
                "org.springframework.boot.data.redis.autoconfigure.DataRedisReactiveAutoConfiguration," +
                "org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration," +
                "org.springframework.boot.data.redis.autoconfigure.RedisRepositoriesAutoConfiguration," +
                "org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration"
})
class SearchServiceApplicationTests {

    @MockitoBean
    private CourseElasticRepository courseElasticRepository;

    @MockitoBean
    private CategoryElasticRepository categoryElasticRepository;

    @MockitoBean
    private CourseSearchCustomRepository courseSearchCustomRepository;

    @MockitoBean
    private ElasticsearchOperations elasticsearchOperations;

    @MockitoBean
    private RedisConnectionFactory redisConnectionFactory;

    @MockitoBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockitoBean
    private VersionedCacheStore versionedCacheStore;

    @MockitoBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void contextLoads() {
    }

}

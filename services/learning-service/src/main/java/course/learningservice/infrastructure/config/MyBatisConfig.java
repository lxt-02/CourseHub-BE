package course.learningservice.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("course.learningservice.infrastructure.persistence.repository")
public class MyBatisConfig {
}

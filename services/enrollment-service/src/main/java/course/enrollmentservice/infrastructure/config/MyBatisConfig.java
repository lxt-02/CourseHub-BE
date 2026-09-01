package course.enrollmentservice.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("course.enrollmentservice.infrastructure.persistence.repository")
public class MyBatisConfig {
}

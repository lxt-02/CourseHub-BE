package course.userservice.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("course.userservice.infrastructure.persistence.repository")
public class MyBatisConfig {
}

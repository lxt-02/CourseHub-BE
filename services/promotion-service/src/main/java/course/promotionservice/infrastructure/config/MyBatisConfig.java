package course.promotionservice.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("course.promotionservice.infrastructure.persistence.repository")
public class MyBatisConfig {
}

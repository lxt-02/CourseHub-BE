package cms.communicationservice.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cms.communicationservice.infrastructure.persistence.repository")
public class MyBatisConfig {
}

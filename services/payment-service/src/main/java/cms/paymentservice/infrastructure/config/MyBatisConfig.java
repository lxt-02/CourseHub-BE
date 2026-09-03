package cms.paymentservice.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cms.paymentservice.infrastructure.persistence.repository")
public class MyBatisConfig {
}

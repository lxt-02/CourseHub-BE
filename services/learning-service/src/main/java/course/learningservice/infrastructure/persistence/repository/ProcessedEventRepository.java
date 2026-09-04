package course.learningservice.infrastructure.persistence.repository;

import course.learningservice.infrastructure.persistence.entity.ProcessedEventEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ProcessedEventRepository {

    Optional<ProcessedEventEntity> findById(@Param("eventId") UUID eventId,
                                            @Param("consumerName") String consumerName);

    boolean existsById(@Param("eventId") UUID eventId,
                       @Param("consumerName") String consumerName);

    int insert(@Param("processedEvent") ProcessedEventEntity processedEvent);

    int deleteById(@Param("eventId") UUID eventId,
                   @Param("consumerName") String consumerName);
}

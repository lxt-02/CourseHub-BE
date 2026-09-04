package course.learningservice.infrastructure.persistence.repository;

import course.learningservice.infrastructure.persistence.entity.OutboxEventEntity;
import course.learningservice.infrastructure.persistence.entity.enums.OutboxEventStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface OutboxEventRepository {

    Optional<OutboxEventEntity> findById(@Param("id") UUID id);

    List<OutboxEventEntity> findByStatus(@Param("status") OutboxEventStatus status);

    List<OutboxEventEntity> findPending(@Param("limit") int limit);

    int insert(@Param("outboxEvent") OutboxEventEntity outboxEvent);

    int updatePublishState(@Param("outboxEvent") OutboxEventEntity outboxEvent);

    int markPublished(@Param("id") UUID id, @Param("publishedAt") Instant publishedAt);

    int markFailed(@Param("id") UUID id, @Param("lastError") String lastError);

    int deleteById(@Param("id") UUID id);
}

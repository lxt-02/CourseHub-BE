package course.userservice.infrastructure.persistence.repository;

import course.userservice.infrastructure.persistence.entity.RoleEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {

    @Select("""
            SELECT id, code, name, description, created_at
            FROM roles
            WHERE id = #{id}
            """)
    Optional<RoleEntity> findById(UUID id);

    @Select("""
            SELECT id, code, name, description, created_at
            FROM roles
            WHERE code = #{code}
            """)
    Optional<RoleEntity> findByCode(String code);

    @Select("""
            SELECT r.id, r.code, r.name, r.description, r.created_at
            FROM roles r
            JOIN user_roles ur ON ur.role_id = r.id
            WHERE ur.user_id = #{userId}
            ORDER BY r.code
            """)
    List<RoleEntity> findByUserId(UUID userId);

    @Insert("""
            INSERT INTO user_roles (user_id, role_id)
            VALUES (#{userId}, #{roleId})
            ON CONFLICT DO NOTHING
            """)
    int assignRoleToUser(UUID userId, UUID roleId);

    @Delete("""
            DELETE FROM user_roles
            WHERE user_id = #{userId}
              AND role_id = #{roleId}
            """)
    int removeRoleFromUser(UUID userId, UUID roleId);
}

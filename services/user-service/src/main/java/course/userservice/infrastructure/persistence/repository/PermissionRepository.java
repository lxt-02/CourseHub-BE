package course.userservice.infrastructure.persistence.repository;

import course.userservice.infrastructure.persistence.entity.PermissionEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository {

    @Select("""
            SELECT id, code, description, created_at
            FROM permissions
            WHERE id = #{id}
            """)
    Optional<PermissionEntity> findById(UUID id);

    @Select("""
            SELECT id, code, description, created_at
            FROM permissions
            WHERE code = #{code}
            """)
    Optional<PermissionEntity> findByCode(String code);

    @Select("""
            SELECT DISTINCT p.id, p.code, p.description, p.created_at
            FROM permissions p
            JOIN role_permissions rp ON rp.permission_id = p.id
            JOIN user_roles ur ON ur.role_id = rp.role_id
            WHERE ur.user_id = #{userId}
            ORDER BY p.code
            """)
    List<PermissionEntity> findByUserId(UUID userId);

    @Select("""
            SELECT p.id, p.code, p.description, p.created_at
            FROM permissions p
            JOIN role_permissions rp ON rp.permission_id = p.id
            WHERE rp.role_id = #{roleId}
            ORDER BY p.code
            """)
    List<PermissionEntity> findByRoleId(UUID roleId);

    @Insert("""
            INSERT INTO role_permissions (role_id, permission_id)
            VALUES (#{roleId}, #{permissionId})
            ON CONFLICT DO NOTHING
            """)
    int assignPermissionToRole(UUID roleId, UUID permissionId);

    @Delete("""
            DELETE FROM role_permissions
            WHERE role_id = #{roleId}
              AND permission_id = #{permissionId}
            """)
    int removePermissionFromRole(UUID roleId, UUID permissionId);
}

package course.userservice.infrastructure.persistence.repository;

import course.userservice.infrastructure.persistence.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    @Select("""
            SELECT id, email, password_hash, full_name, avatar_url, bio, status,
                   email_verified, created_at, updated_at
            FROM users
            WHERE id = #{id}
            """)
    Optional<UserEntity> findById(UUID id);

    @Select("""
            SELECT id, email, password_hash, full_name, avatar_url, bio, status,
                   email_verified, created_at, updated_at
            FROM users
            WHERE email = #{email}
            """)
    Optional<UserEntity> findByEmail(String email);

    @Select("""
            SELECT id, email, password_hash, full_name, avatar_url, bio, status,
                   email_verified, created_at, updated_at
            FROM users
            ORDER BY created_at DESC
            """)
    List<UserEntity> findAll();

    @Insert("""
            INSERT INTO users (email, password_hash, full_name, avatar_url, bio, status, email_verified)
            VALUES (#{email}, #{passwordHash}, #{fullName}, #{avatarUrl}, #{bio}, #{status}, #{emailVerified})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(UserEntity user);

    @Update("""
            UPDATE users
            SET email = #{email},
                password_hash = #{passwordHash},
                full_name = #{fullName},
                avatar_url = #{avatarUrl},
                bio = #{bio},
                status = #{status},
                email_verified = #{emailVerified},
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int update(UserEntity user);

    @Delete("""
            DELETE FROM users
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}

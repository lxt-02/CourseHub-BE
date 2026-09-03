package course.userservice.infrastructure.persistence.repository;

import course.userservice.infrastructure.persistence.entity.OauthAccountEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OauthAccountRepository {

    @Select("""
            SELECT id, user_id, provider, provider_user_id, provider_email, created_at
            FROM oauth_accounts
            WHERE id = #{id}
            """)
    Optional<OauthAccountEntity> findById(UUID id);

    @Select("""
            SELECT id, user_id, provider, provider_user_id, provider_email, created_at
            FROM oauth_accounts
            WHERE provider = #{provider}
              AND provider_user_id = #{providerUserId}
            """)
    Optional<OauthAccountEntity> findByProviderAndProviderUserId(String provider, String providerUserId);

    @Select("""
            SELECT id, user_id, provider, provider_user_id, provider_email, created_at
            FROM oauth_accounts
            WHERE user_id = #{userId}
            ORDER BY created_at DESC
            """)
    List<OauthAccountEntity> findByUserId(UUID userId);

    @Insert("""
            INSERT INTO oauth_accounts (user_id, provider, provider_user_id, provider_email)
            VALUES (#{userId}, #{provider}, #{providerUserId}, #{providerEmail})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(OauthAccountEntity oauthAccount);
}

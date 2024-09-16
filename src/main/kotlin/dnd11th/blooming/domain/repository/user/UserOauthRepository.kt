package dnd11th.blooming.domain.repository.user

import dnd11th.blooming.domain.entity.user.OauthProvider
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.entity.user.UserOauthInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserOauthRepository : JpaRepository<UserOauthInfo, Long> {
    @Query(
        "SELECT uoi FROM UserOauthInfo uoi JOIN FETCH uoi.user WHERE uoi.email = :email AND uoi.provider = :provider",
    )
    fun findByEmailAndProvider(
        @Param("email") email: String,
        @Param("provider") provider: OauthProvider,
    ): UserOauthInfo?

    @Modifying
    @Query("DELETE FROM UserOauthInfo uoi WHERE uoi.user = :user")
    fun deleteByUser(user: User)
}

package dnd11th.blooming.domain.repository.user

import dnd11th.blooming.domain.entity.user.OauthProvider
import dnd11th.blooming.domain.entity.user.UserOauthInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserOauthRepository : JpaRepository<UserOauthInfo, Long> {
    fun findByEmailAndProvider(
        email: String,
        provider: OauthProvider,
    ): UserOauthInfo?
}

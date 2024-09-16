package dnd11th.blooming.domain.core.repository.user

import dnd11th.blooming.domain.core.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>

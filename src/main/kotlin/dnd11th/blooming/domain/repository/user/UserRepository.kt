package dnd11th.blooming.domain.repository.user

import dnd11th.blooming.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>

package dnd11th.blooming.core.repository.user

import dnd11th.blooming.core.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>

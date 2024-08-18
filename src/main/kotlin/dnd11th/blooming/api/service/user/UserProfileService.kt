package dnd11th.blooming.api.service.user

import dnd11th.blooming.api.dto.user.MyProfileResponse
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.repository.MyPlantRepository
import org.springframework.stereotype.Service

@Service
class UserProfileService(
    private val myPlantRepository: MyPlantRepository,
) {
    fun findProfile(user: User): MyProfileResponse {
        val myPlantCount = myPlantRepository.countByUser(user)
        return MyProfileResponse.of(user, myPlantCount)
    }
}

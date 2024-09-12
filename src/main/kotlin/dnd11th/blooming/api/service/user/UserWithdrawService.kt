package dnd11th.blooming.api.service.user

import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.repository.ImageRepository
import dnd11th.blooming.domain.repository.LocationRepository
import dnd11th.blooming.domain.repository.myplant.MyPlantRepository
import dnd11th.blooming.domain.repository.user.UserOauthRepository
import dnd11th.blooming.domain.repository.user.UserRepository
import org.springframework.stereotype.Service

@Service
class UserWithdrawService(
    private val userRepository: UserRepository,
    private val userOauthRepository: UserOauthRepository,
    private val locationRepository: LocationRepository,
    private val myPlantRepository: MyPlantRepository,
    private val imageRepository: ImageRepository,
) {
    fun withdraw(loginUser: User) {
        val myPlantByUser: List<MyPlant> = myPlantRepository.findAllByUser(loginUser)
        myPlantByUser.forEach { myPlant: MyPlant -> imageRepository.deleteAllByMyPlant(myPlant) }
        myPlantRepository.deleteAllByUser(loginUser)
        locationRepository.deleteByUser(loginUser)
        userOauthRepository.deleteByUser(loginUser)
        userRepository.delete(loginUser)
    }
}

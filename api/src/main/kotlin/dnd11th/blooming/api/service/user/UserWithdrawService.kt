package dnd11th.blooming.api.service.user

import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.core.entity.myplant.MyPlant
import dnd11th.blooming.core.entity.user.User
import dnd11th.blooming.core.repository.image.ImageRepository
import dnd11th.blooming.core.repository.location.LocationRepository
import dnd11th.blooming.core.repository.myplant.MyPlantRepository
import dnd11th.blooming.core.repository.user.UserOauthRepository
import dnd11th.blooming.core.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserWithdrawService(
    private val userRepository: UserRepository,
    private val userOauthRepository: UserOauthRepository,
    private val locationRepository: LocationRepository,
    private val myPlantRepository: MyPlantRepository,
    private val imageRepository: ImageRepository,
) {
    fun withdraw(loginUser: User) {
        val user: User =
            userRepository.findById(loginUser.id!!).orElseThrow {
                throw NotFoundException(ErrorType.USER_NOT_FOUND)
            }
        val myPlantByUser: List<MyPlant> = myPlantRepository.findAllByUser(user)
        imageRepository.deleteAllByMyPlantIn(myPlantByUser)
        myPlantRepository.deleteAllByUser(user)
        locationRepository.deleteAllByUser(user)
        userOauthRepository.deleteByUser(user)
        userRepository.delete(user)
    }
}

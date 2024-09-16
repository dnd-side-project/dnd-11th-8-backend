package dnd11th.blooming.api.service.user

import dnd11th.blooming.api.dto.user.MyProfileResponse
import dnd11th.blooming.domain.core.entity.user.AlarmTime
import dnd11th.blooming.domain.core.entity.user.User
import dnd11th.blooming.domain.core.repository.myplant.MyPlantRepository
import dnd11th.blooming.domain.core.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserProfileService(
    private val myPlantRepository: MyPlantRepository,
    private val userRepository: UserRepository,
) {
    fun findProfile(user: User): MyProfileResponse {
        val myPlantCount = myPlantRepository.countByUser(user)
        return MyProfileResponse.of(user, myPlantCount)
    }

    @Transactional
    fun updateNickname(
        user: User,
        nickname: String,
    ) {
        user.updateNickname(nickname)
        userRepository.save(user)
    }

    @Transactional
    fun updateAlarmStatus(
        user: User,
        alarmStatus: Boolean,
    ) {
        user.updateAlarmStatus(alarmStatus)
        userRepository.save(user)
    }

    @Transactional
    fun updateAlarmTime(
        user: User,
        alarmTime: AlarmTime,
    ) {
        user.updateAlarmTime(alarmTime)
        userRepository.save(user)
    }
}

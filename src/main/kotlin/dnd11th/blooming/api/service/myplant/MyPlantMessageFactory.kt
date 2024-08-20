package dnd11th.blooming.api.service.myplant

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Period

@Component
class MyPlantMessageFactory {
    companion object {
        private const val WATER_TITLE = "마지막으로 물 준 날"
        private const val FERTILIZER_TITLE = "비료주기"
        private const val SAVED_MESSAGE = "등록 되었습니다."
        private const val NOT_EXIST_MESSAGE = "기록없음"
        private val greetingMessageList =
            listOf(
                "%s님 좋아요!\n초보식집사로서 멋진 데뷔네요.",
            )
        private val healthCheckMessageList =
            listOf(
                "환기는 시켜주셨나요?",
                "식물에 붙은 먼지는 잘 털어 주셨나요?",
                "애정있게 쳐다보셨나요?",
                "잎의 색이 건강해보이나요?",
                "흙의 상태를 점검하셨나요?",
                "식물이 자라는 방향을 바꿔 주셨나요?",
                "해충이 없는지 확인하셨나요?",
            )
    }

    fun createGreetingMessage(username: String): String {
        return greetingMessageList.random().format(username)
    }

    fun createWateredTitle(): String = WATER_TITLE

    fun createWateredInfo(
        wateredDate: LocalDate?,
        now: LocalDate,
    ): String {
        wateredDate ?: return NOT_EXIST_MESSAGE

        val daysPeriod = Period.between(wateredDate, now).days

        if (daysPeriod <= 0) {
            return "${wateredDate}\n오늘"
        }

        return "${wateredDate}\n${daysPeriod}일전"
    }

    fun createFertilizerTitle(): String = FERTILIZER_TITLE

    fun createFertilizerInfo(
        fertilizerDate: LocalDate?,
        now: LocalDate,
    ): String {
        fertilizerDate ?: return NOT_EXIST_MESSAGE

        val monthsPeriod = Period.between(fertilizerDate, now).months

        if (monthsPeriod <= 0) {
            return "이번 달"
        }

        return "${monthsPeriod}개월 이내"
    }

    fun createHealthCheckMessage(): String {
        return healthCheckMessageList.random()
    }

    fun createSaveMessage(): String = SAVED_MESSAGE
}

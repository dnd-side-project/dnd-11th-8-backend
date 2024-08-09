package dnd11th.blooming.api.service.myplant

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Period

@Component
class MyPlantMessageFactory {
    private val wateredTitle = "마지막으로 물 준 날"
    private val fertilizerTitle = "비료주기"

    fun createWateredTitle(): String = wateredTitle

    fun createWateredInfo(
        wateredDate: LocalDate,
        now: LocalDate,
    ): String {
        val daysPeriod = Period.between(wateredDate, now).days

        if (daysPeriod <= 0) {
            return "${wateredDate}\n오늘"
        }

        return "${wateredDate}\n${daysPeriod}일전"
    }

    fun createFertilizerTitle(): String = fertilizerTitle

    fun createFertilizerInfo(
        fertilizerDate: LocalDate,
        now: LocalDate,
    ): String {
        val monthsPeriod = Period.between(fertilizerDate, now).months

        if (monthsPeriod <= 0) {
            return "이번 달"
        }

        return "${monthsPeriod}개월 이내"
    }
}

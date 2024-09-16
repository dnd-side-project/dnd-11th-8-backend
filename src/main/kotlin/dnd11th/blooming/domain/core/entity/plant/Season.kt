package dnd11th.blooming.domain.core.entity.plant

import java.time.LocalDate
import java.time.Month

enum class Season(
    val displayName: String,
) {
    SPRING("봄"),
    SUMMER("여름"),
    FALL("가을"),
    WINTER("겨울"),
    ;

    companion object {
        fun getSeason(month: Month = LocalDate.now().month): Season {
            return when (month) {
                Month.MARCH, Month.APRIL, Month.MAY -> SPRING
                Month.JUNE, Month.JULY, Month.AUGUST -> SUMMER
                Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER -> FALL
                Month.DECEMBER, Month.JANUARY, Month.FEBRUARY -> WINTER
            }
        }
    }
}

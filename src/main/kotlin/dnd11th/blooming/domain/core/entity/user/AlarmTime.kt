package dnd11th.blooming.domain.core.entity.user

import java.time.LocalTime

enum class AlarmTime(val code: Int, val displayName: String, val startHour: Int, val endHour: Int) {
    TIME_5_6(1, "5:00-6:00", 5, 6),
    TIME_6_7(2, "6:00-7:00", 6, 7),
    TIME_7_8(3, "7:00-8:00", 7, 8),
    TIME_8_9(4, "8:00-9:00", 8, 9),
    TIME_9_10(5, "9:00-10:00", 9, 10),
    TIME_10_11(6, "10:00-11:00", 10, 11),
    TIME_11_12(7, "11:00-12:00", 11, 12),
    TIME_12_13(8, "12:00-13:00", 12, 13),
    TIME_13_14(9, "13:00-14:00", 13, 14),
    TIME_14_15(10, "14:00-15:00", 14, 15),
    TIME_15_16(11, "15:00-16:00", 15, 16),
    TIME_16_17(12, "16:00-17:00", 16, 17),
    TIME_17_18(13, "17:00-18:00", 17, 18),
    TIME_18_19(14, "18:00-19:00", 18, 19),
    TIME_19_20(15, "19:00-20:00", 19, 20),
    TIME_20_21(16, "20:00-21:00", 20, 21),
    TIME_21_22(17, "21:00-22:00", 21, 22),
    TIME_22_23(18, "22:00-23:00", 22, 23),
    TIME_23_24(19, "23:00-24:00", 23, 24),
    ;

    companion object {
        fun from(code: Int): AlarmTime {
            return entries.find { it.code == code }
                ?: throw IllegalArgumentException("Invalid AlarmTime code: $code")
        }

        fun fromHour(now: LocalTime): AlarmTime {
            return entries.find { now.hour in it.startHour until it.endHour }
                ?: throw IllegalArgumentException("No matching AlarmTime for hour: ${now.hour}")
        }
    }
}

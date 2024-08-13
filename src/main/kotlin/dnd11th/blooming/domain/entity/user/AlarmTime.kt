package dnd11th.blooming.domain.entity.user

enum class AlarmTime(val code: Int, val displayName: String) {
    TIME_5_6(1, "5:00-6:00"),
    TIME_6_7(2, "6:00-7:00"),
    TIME_7_8(3, "7:00-8:00"),
    TIME_8_9(4, "8:00-9:00"),
    TIME_9_10(5, "9:00-10:00"),
    TIME_10_11(6, "10:00-11:00"),
    TIME_11_12(7, "11:00-12:00"),
    TIME_12_13(8, "12:00-13:00"),
    TIME_13_14(9, "13:00-14:00"),
    TIME_14_15(10, "14:00-15:00"),
    TIME_15_16(11, "15:00-16:00"),
    TIME_16_17(12, "16:00-17:00"),
    TIME_17_18(13, "17:00-18:00"),
    TIME_18_19(14, "18:00-19:00"),
    TIME_19_20(15, "19:00-20:00"),
    TIME_20_21(16, "20:00-21:00"),
    TIME_21_22(17, "21:00-22:00"),
    TIME_22_23(18, "22:00-23:00"),
    TIME_23_24(19, "23:00-24:00"),
    ;

    companion object {
        fun from(code: Int): AlarmTime {
            return entries.find { it.code == code }
                ?: throw IllegalArgumentException("Invalid AlarmTime code: $code")
        }
    }
}

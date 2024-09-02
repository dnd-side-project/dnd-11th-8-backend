package dnd11th.blooming.common.util

class HangulUtil {
    companion object {
        fun getEunOrNun(str: String): String {
            return when (isKorStringEndsWithBatchim(str)) {
                true -> "은"
                false -> "는"
            }
        }

        private fun isKorStringEndsWithBatchim(str: String): Boolean {
            if (str.isBlank()) return false
            val lastChar = str.last()
            if (lastChar !in '가'..'힣') return false
            return (lastChar.code - '가'.code) % 28 != 0
        }
    }
}

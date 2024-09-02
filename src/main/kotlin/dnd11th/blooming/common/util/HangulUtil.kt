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

        fun getSearchPattern(hangul: String): String {
            val decomposedHangul = decomposeHangul(hangul)
            return "%$decomposedHangul%"
        }

        private val CHO_SUNG: CharArray =
            charArrayOf('ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')
        private val JUNG_SUNG: CharArray =
            charArrayOf('ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ')
        private val JONG_SUNG: CharArray =
            charArrayOf('\u0000', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')

        private fun decomposeHangul(hangul: String): String {
            val result = StringBuilder()

            for (c in hangul.toCharArray()) {
                if (c in '가'..'힣') { // 한글 완성형
                    val base = c.code - '가'.code
                    val cho = base / (21 * 28)
                    val jung = (base % (21 * 28)) / 28
                    val jong = base % 28

                    result.append(CHO_SUNG[cho]).append(JUNG_SUNG[jung])
                    if (jong != 0) {
                        result.append(JONG_SUNG[jong])
                    }
                } else {
                    result.append(c) // 한글이 아닌 문자는 그대로 추가
                }
            }

            return result.toString()
        }
    }
}

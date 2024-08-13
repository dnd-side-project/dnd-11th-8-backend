package dnd11th.blooming.api.dto.home

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Home Response",
    description = "홈 화면 응답",
)
data class HomeResponse(
    @field:Schema(name = "인사말", example = "김루밍님 반가워요!\n좋은 하루 되세요!")
    val greetingMessage: String,
    @field:Schema(name = "내 식물 정보 리스트")
    val myPlantInfo: List<MyPlantHomeResponse>,
    // TODO : 식물 일러스트 응답 추가 필요
) {
    companion object {
        fun from(
            greetingMessage: String,
            myPlantInfo: List<MyPlantHomeResponse>,
        ): HomeResponse =
            HomeResponse(
                greetingMessage = greetingMessage,
                myPlantInfo = myPlantInfo,
            )
    }
}

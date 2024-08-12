package dnd11th.blooming.api.dto.home

data class HomeResponse(
    val greetingMessage: String,
    val myPlantInfo: List<MyPlantHomeResponse>,
    // TODO : 식물 일러스트 응답 추가 필요
    // val illustUrl: String,
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

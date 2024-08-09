package dnd11th.blooming.api.dto.home

data class HomeResponse(
    val greetingMessage: String,
    val myPlantInfo: List<MyPlantHomeResponse>,
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

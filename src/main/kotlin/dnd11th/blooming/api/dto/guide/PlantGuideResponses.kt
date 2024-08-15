package dnd11th.blooming.api.dto.guide

import io.swagger.v3.oas.annotations.media.Schema

data class PlantGuideResponse(
    @field:Schema(description = "식물 한글명", example = "몬스테라 델리오사")
    val korName: String,
    @field:Schema(description = "식물 영어명", example = "Mostera Deliosa")
    val engName: String,
    @field:Schema(description = "식물 이미지 URL", example = "image.com")
    val imageUrl: String,
    @field:Schema(description = "식물 태그 리스트", example = "[\"초보식집사\", \"인기\"]")
    val tag: List<String>,
    @field:Schema(description = "한눈에 보는 식물 정보")
    val simpleView: PlantGuideSimpleViewResponse,
    @field:Schema(description = "자세한 식물 정보")
    val detailView: PlantGuideDetailViewResponse,
)

data class PlantGuideSimpleViewResponse(
    @field:Schema(description = "난이도 정보")
    val difficulty: PlantGuideSimpleResponse,
    @field:Schema(description = "물주기 정보")
    val water: PlantGuideSimpleResponse,
    @field:Schema(description = "병해충 정보")
    val pests: PlantGuideSimpleResponse,
    @field:Schema(description = "장소 정보")
    val location: PlantGuideSimpleResponse,
    @field:Schema(description = "크기 정보")
    val size: PlantGuideSimpleResponse,
    @field:Schema(description = "독성 정보")
    val toxicity: PlantGuideSimpleResponse,
    @field:Schema(description = "온도 정보")
    val temperature: PlantGuideSimpleResponse,
    @field:Schema(description = "비료 정보")
    val fertilizer: PlantGuideSimpleResponse,
)

data class PlantGuideSimpleResponse(
    val title: String,
    val description: String,
)

data class PlantGuideDetailViewResponse(
    @field:Schema(description = "상세한 물주기 정보")
    val water: DetailWaterResponse,
    @field:Schema(description = "상세한 빛 정보")
    val light: DetailLightResponse,
    @field:Schema(description = "상세한 습도 정보")
    val humidity: DetailHumidityResponse,
    @field:Schema(description = "상세한 독성 정보")
    val toxicity: DetailToxicityResponse,
    @field:Schema(description = "상세한 병충해 정보")
    val pests: DetailPestsResponse,
)

data class DetailWaterResponse(
    @field:Schema(example = "물주기")
    val title: String,
    @field:Schema(example = "봄,여름,가을")
    val springsummerfallSubTitle: String,
    @field:Schema(example = "흙은 항상 촉촉하게 유지해주세요. 다만 물에 잠기지 않도록 주의하는 것이 좋으며 일주일에 한번 정도가 적당해요.")
    val springsummerfallDescription: String,
    @field:Schema(example = "겨울")
    val winterSubTitle: String,
    @field:Schema(example = "토양 표면이 말랐을 때 충분히 물을 주세요.")
    val winterDescription: String,
    @field:Schema(example = "빗물을 주면 토양이 산성화 될 수도 있어요. 따뜻한 물을 사용하고 더운 여름에는 젖은 천으로 잎을 닦아 주고, 분무해주세요.")
    val addition: String,
)

data class DetailLightResponse(
    @field:Schema(example = "빛")
    val title: String,
    @field:Schema(example = "광요구도")
    val lightSubTitle: String,
    @field:Schema(example = "중간 광도(800~1,500Lux), 높은 광도(1,500~10,000Lux)")
    val lightDescription: String,
    @field:Schema(example = "몬스테라는 간접적인 밝은 빛을 가장 좋아합니다. 직사광선은 잎을 태울 수 있으므로 피하는 것이 좋아요.")
    val addition: String,
)

data class DetailHumidityResponse(
    @field:Schema(example = "습도")
    val title: String,
    @field:Schema(example = "몬스테라는 높은 습도를 좋아해요. 60% 이상의 습도가 가장 이상적이에요.")
    val description: String,
    @field:Schema(example = "습도를 높이기 위해 자주 분무하거나, 식물 주변에 물그릇을 놓아 습도를 유지할 수 있어요. 가습기를 사용하는 것도 좋아요.")
    val addition: String,
)

data class DetailToxicityResponse(
    @field:Schema(example = "독성")
    val title: String,
    @field:Schema(example = "몬스테라는 잎과 줄기에 독성이 있어 반려동물과 어린 아이들이 먹지 않도록 주의해야 합니다. 섭취 시 입 안의 자극, 구토, 설사 등의 증상이 나타날 수 있어요.")
    val description: String,
)

data class DetailPestsResponse(
    @field:Schema(example = "병충해")
    val title: String,
    @field:Schema(example = "몬스테라는 응애와 깍지벌레에 취약해요. 주변에 벌레시체등이 보인다면, 병충해의 신호일 수 있습니다.")
    val description: String,
)

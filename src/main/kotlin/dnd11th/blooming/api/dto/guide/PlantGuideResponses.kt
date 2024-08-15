package dnd11th.blooming.api.dto.guide

data class PlantGuideResponse(
    val korName: String,
    val engName: String,
    val imageUrl: String,
    val tag: List<String>,
    val simpleView: PlantGuideSimpleViewResponse,
    val detailView: PlantGuideDetailViewResponse,
)

data class PlantGuideSimpleViewResponse(
    val difficulty: SimpleResponse,
    val water: SimpleResponse,
    val pests: SimpleResponse,
    val location: SimpleResponse,
    val size: SimpleResponse,
    val toxicity: SimpleResponse,
    val temperature: SimpleResponse,
)

data class SimpleResponse(
    val title: String,
    val description: String,
)

data class PlantGuideDetailViewResponse(
    val water: DetailWaterResponse,
    val light: DetailLightResponse,
    val humidity: DetailHumidityResponse,
    val toxicity: DetailToxicityResponse,
    val pests: DetailPestsResponse,
)

data class DetailWaterResponse(
    val title: String,
    val springsummerfallSubTitle: String,
    val springsummerfallDescription: String,
    val winterSubTitle: String,
    val winterDescription: String,
    val addition: String,
)

data class DetailLightResponse(
    val title: String,
    val lightSubTitle: String,
    val lightDescription: String,
    val addition: String,
)

data class DetailHumidityResponse(
    val title: String,
    val description: String,
    val addition: String,
)

data class DetailToxicityResponse(
    val title: String,
    val description: String,
)

data class DetailPestsResponse(
    val title: String,
    val description: String,
)

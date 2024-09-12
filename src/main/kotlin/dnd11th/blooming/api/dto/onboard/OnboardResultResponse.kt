package dnd11th.blooming.api.dto.onboard

import dnd11th.blooming.domain.entity.onboard.OnboardingResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Onboard Result Response",
    description = "온보딩 결과 응답",
)
data class OnboardResultResponse(
    @field:Schema(description = "접두사", example = "당신의 존재감을 확실히 비추는")
    val subTitle: String,
    @field:Schema(description = "결과", example = "매력적인 몬스테라")
    val result: String,
    @field:Schema(description = "일러스트 url", example = "illust.com/3")
    val illustUrl: String,
    @field:Schema(description = "설명", example = "몬스테라의 특징인 독특하고 큰 잎처럼 어느 장소에서든 돋보이는 매력덩어리인 당신, 몬스테라 식물과 가장 잘 어울려요.")
    val description: String,
) {
    companion object {
        fun from(result: OnboardingResult): OnboardResultResponse =
            OnboardResultResponse(
                subTitle = result.subTitle,
                result = result.result,
                illustUrl = result.illustUrl,
                description = result.description,
            )
    }
}

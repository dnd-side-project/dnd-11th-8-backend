package dnd11th.blooming.domain.entity.onboard

import dnd11th.blooming.domain.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class OnboardingResult(
    @Column
    var version: Int,
    @Column
    var result: String,
    @Column
    var subTitle: String,
    @Column
    var illustUrl: String,
    @Column
    var description: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun calculateResult(
            resultSet: List<OnboardingResult>,
            selectedResult: List<OnboardingResult>,
        ): OnboardingResult {
            // 선택된 결과들의 빈도를 계산
            val frequencyMap: Map<OnboardingResult, Int> =
                selectedResult
                    .groupingBy { it }
                    .eachCount()

            // resultSet 중에서 선택된 결과들 중 가장 빈도가 높은 결과를 찾음
            return resultSet
                .maxByOrNull { result ->
                    frequencyMap.getOrDefault(result, 0)
                } ?: throw NoSuchElementException("No result found in the resultSet")
        }
    }
}

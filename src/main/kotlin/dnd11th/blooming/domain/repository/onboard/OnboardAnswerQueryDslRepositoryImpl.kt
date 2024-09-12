package dnd11th.blooming.domain.repository.onboard

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import dnd11th.blooming.domain.entity.onboard.OnboardingResult
import dnd11th.blooming.domain.entity.onboard.QOnboardingAnswer
import org.springframework.stereotype.Repository

@Repository
class OnboardAnswerQueryDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : OnboardAnswerQueryDslRepository {
    override fun findAllOnboardingResultByQuestionNumberAndAnswerNumberIn(resultPairs: List<Pair<Int, Int>>): List<OnboardingResult> {
        val qOnboardingAnswer = QOnboardingAnswer.onboardingAnswer

        return queryFactory
            .select(qOnboardingAnswer.onboardingResult)
            .from(qOnboardingAnswer)
            .where(inResultPairs(qOnboardingAnswer, resultPairs))
            .fetch()
    }

    private fun inResultPairs(
        qOnboardingAnswer: QOnboardingAnswer,
        resultPairs: List<Pair<Int, Int>>,
    ): BooleanExpression? {
        val conditions: List<BooleanExpression> =
            resultPairs.map { (questionNumber, answerNumber) ->
                qOnboardingAnswer.onboardingQuestion.questionNumber.eq(questionNumber)
                    .and(qOnboardingAnswer.answerNumber.eq(answerNumber))
            }
        return if (conditions.isNotEmpty()) {
            Expressions.allOf(*conditions.toTypedArray())
        } else {
            null
        }
    }
}

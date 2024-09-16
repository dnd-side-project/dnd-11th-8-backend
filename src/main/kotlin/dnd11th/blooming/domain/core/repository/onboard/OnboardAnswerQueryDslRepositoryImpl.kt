package dnd11th.blooming.domain.core.repository.onboard

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import dnd11th.blooming.domain.core.entity.onboard.OnboardingAnswer
import dnd11th.blooming.domain.core.entity.onboard.QOnboardingAnswer
import org.springframework.stereotype.Repository

@Repository
class OnboardAnswerQueryDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : OnboardAnswerQueryDslRepository {
    override fun findAllByQuestionNumberAndAnswerNumberIn(resultPairs: List<Pair<Int, Int>>): List<OnboardingAnswer> {
        val onboardingAnswer = QOnboardingAnswer.onboardingAnswer

        return queryFactory
            .select(onboardingAnswer)
            .from(onboardingAnswer)
            .where(inResultPairs(onboardingAnswer, resultPairs))
            .fetch()
    }

    private fun inResultPairs(
        qOnboardingAnswer: QOnboardingAnswer,
        resultPairs: List<Pair<Int, Int>>,
    ): BooleanExpression {
        return resultPairs
            .map { (questionNumber, answerNumber) ->
                qOnboardingAnswer.onboardingQuestion.questionNumber.eq(questionNumber)
                    .and(qOnboardingAnswer.answerNumber.eq(answerNumber))
            }
            .reduce { acc, expr -> acc.or(expr) }
    }
}

package dnd11th.blooming.api.service.onboard

import dnd11th.blooming.api.dto.onboard.OnboardResultRequest
import dnd11th.blooming.domain.entity.onboard.OnboardingAnswer
import dnd11th.blooming.domain.entity.onboard.OnboardingQuestion
import dnd11th.blooming.domain.entity.onboard.OnboardingResult
import dnd11th.blooming.domain.repository.onboard.OnboardAnswerRepository
import dnd11th.blooming.domain.repository.onboard.OnboardQuestionRepository
import dnd11th.blooming.domain.repository.onboard.OnboardResultRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class OnboardServiceIntegrationTest : DescribeSpec() {
    @Autowired
    lateinit var onboardService: OnboardService

    @Autowired
    lateinit var onboardAnswerRepository: OnboardAnswerRepository

    @Autowired
    lateinit var onboardQuestionRepository: OnboardQuestionRepository

    @Autowired
    lateinit var onboardResultRepository: OnboardResultRepository

    init {
        afterEach {
            onboardAnswerRepository.deleteAllInBatch()
            onboardQuestionRepository.deleteAllInBatch()
            onboardResultRepository.deleteAllInBatch()
        }

        describe("온보딩 질문지 조회") {
            beforeTest {
                val question1 = onboardQuestionRepository.save(OnboardingQuestion(1, 1, "1번 질문"))
                val question2 = onboardQuestionRepository.save(OnboardingQuestion(1, 2, "2번 질문"))
                onboardQuestionRepository.saveAll(
                    listOf(
                        OnboardingQuestion(0, 1, "1번 질문"),
                        OnboardingQuestion(0, 2, "2번 질문"),
                    ),
                )
                onboardAnswerRepository.saveAll(
                    listOf(
                        OnboardingAnswer(1, "1-1번 응답").also { it.onboardingQuestion = question1 },
                        OnboardingAnswer(2, "2-1번 응답").also { it.onboardingQuestion = question1 },
                        OnboardingAnswer(3, "3-1번 응답").also { it.onboardingQuestion = question1 },
                        OnboardingAnswer(4, "4-1번 응답").also { it.onboardingQuestion = question1 },
                        OnboardingAnswer(1, "1-2번 응답").also { it.onboardingQuestion = question2 },
                        OnboardingAnswer(2, "2-2번 응답").also { it.onboardingQuestion = question2 },
                        OnboardingAnswer(3, "3-2번 응답").also { it.onboardingQuestion = question2 },
                        OnboardingAnswer(4, "4-2번 응답").also { it.onboardingQuestion = question2 },
                    ),
                )
            }
            context("온보딩 질문지를 조회하면") {
                val response = onboardService.findScripts()
                it("가장 최신의 질문지 전체가 반환되어야 한다.") {
                    response.version shouldBe 1
                    response.questions.size shouldBe 2

                    response.questions[0].question shouldBe "1번 질문"
                    response.questions[0].questionNumber shouldBe 1
                    response.questions[0].answers.size shouldBe 4
                    response.questions[0].answers[0].answer shouldBe "1-1번 응답"
                    response.questions[0].answers[1].answer shouldBe "2-1번 응답"
                    response.questions[0].answers[2].answer shouldBe "3-1번 응답"
                    response.questions[0].answers[3].answer shouldBe "4-1번 응답"

                    response.questions[1].question shouldBe "2번 질문"
                    response.questions[1].questionNumber shouldBe 2
                    response.questions[1].answers.size shouldBe 4
                    response.questions[1].answers[0].answer shouldBe "1-2번 응답"
                    response.questions[1].answers[1].answer shouldBe "2-2번 응답"
                    response.questions[1].answers[2].answer shouldBe "3-2번 응답"
                    response.questions[1].answers[3].answer shouldBe "4-2번 응답"
                }
            }
        }

        describe("온보딩 응답 제출") {
            beforeTest {
                val question1 = onboardQuestionRepository.save(OnboardingQuestion(1, 1, "1번 질문"))
                val question2 = onboardQuestionRepository.save(OnboardingQuestion(1, 2, "2번 질문"))
                val question3 = onboardQuestionRepository.save(OnboardingQuestion(1, 3, "3번 질문"))
                val question4 = onboardQuestionRepository.save(OnboardingQuestion(1, 4, "4번 질문"))
                onboardQuestionRepository.saveAll(
                    listOf(
                        OnboardingQuestion(0, 1, "1번 과거 질문"),
                        OnboardingQuestion(0, 2, "2번 과거 질문"),
                    ),
                )
                val result1 = onboardResultRepository.save(OnboardingResult(1, "결과 1", "부제 1", "일러스트 1", "설명 1"))
                val result2 = onboardResultRepository.save(OnboardingResult(1, "결과 2", "부제 2", "일러스트 2", "설명 2"))
                val result3 = onboardResultRepository.save(OnboardingResult(1, "결과 3", "부제 3", "일러스트 3", "설명 3"))
                onboardResultRepository.saveAll(
                    listOf(
                        OnboardingResult(0, "1번 과거 결과", "부제 1", "일러스트 1", "설명 1"),
                        OnboardingResult(0, "2번 과거 결과", "부제 1", "일러스트 1", "설명 1"),
                    ),
                )
                onboardAnswerRepository.saveAll(
                    listOf(
                        OnboardingAnswer(1, "1번 결과 가중치 응답").also {
                            it.onboardingQuestion = question1
                            it.onboardingResult = result1
                        },
                        OnboardingAnswer(2, "2번 결과 가중치 응답").also {
                            it.onboardingQuestion = question1
                            it.onboardingResult = result2
                        },
                        OnboardingAnswer(3, "3번 결과 가중치 응답").also {
                            it.onboardingQuestion = question1
                            it.onboardingResult = result3
                        },
                        OnboardingAnswer(4, "1번 결과 가중치 응답").also {
                            it.onboardingQuestion = question1
                            it.onboardingResult = result1
                        },
                        OnboardingAnswer(1, "2번 결과 가중치 응답").also {
                            it.onboardingQuestion = question2
                            it.onboardingResult = result2
                        },
                        OnboardingAnswer(2, "3번 결과 가중치 응답").also {
                            it.onboardingQuestion = question2
                            it.onboardingResult = result3
                        },
                        OnboardingAnswer(3, "1번 결과 가중치 응답").also {
                            it.onboardingQuestion = question2
                            it.onboardingResult = result1
                        },
                        OnboardingAnswer(4, "2번 결과 가중치 응답").also {
                            it.onboardingQuestion = question2
                            it.onboardingResult = result2
                        },
                        OnboardingAnswer(1, "3번 결과 가중치 응답").also {
                            it.onboardingQuestion = question3
                            it.onboardingResult = result3
                        },
                        OnboardingAnswer(2, "1번 결과 가중치 응답").also {
                            it.onboardingQuestion = question3
                            it.onboardingResult = result1
                        },
                        OnboardingAnswer(3, "2번 결과 가중치 응답").also {
                            it.onboardingQuestion = question3
                            it.onboardingResult = result2
                        },
                        OnboardingAnswer(4, "3번 결과 가중치 응답").also {
                            it.onboardingQuestion = question3
                            it.onboardingResult = result3
                        },
                        OnboardingAnswer(1, "1번 결과 가중치 응답").also {
                            it.onboardingQuestion = question4
                            it.onboardingResult = result1
                        },
                        OnboardingAnswer(2, "2번 결과 가중치 응답").also {
                            it.onboardingQuestion = question4
                            it.onboardingResult = result2
                        },
                        OnboardingAnswer(3, "3번 결과 가중치 응답").also {
                            it.onboardingQuestion = question4
                            it.onboardingResult = result3
                        },
                        OnboardingAnswer(4, "1번 결과 가중치 응답").also {
                            it.onboardingQuestion = question4
                            it.onboardingResult = result1
                        },
                    ),
                )
            }
            context("온보딩 응답을 제출하면") {
                val request =
                    listOf(
                        OnboardResultRequest(1, 1),
                        OnboardResultRequest(2, 3),
                        OnboardResultRequest(3, 3),
                        OnboardResultRequest(4, 3),
                    )
                val result = onboardService.submitScripts(1, request)
                it("정상적으로 응답 결과가 반환되어야 한다.") {
                    result.result shouldBe "결과 1"
                    result.subTitle shouldBe "부제 1"
                    result.illustUrl shouldBe "일러스트 1"
                    result.description shouldBe "설명 1"
                }
            }
        }
    }
}

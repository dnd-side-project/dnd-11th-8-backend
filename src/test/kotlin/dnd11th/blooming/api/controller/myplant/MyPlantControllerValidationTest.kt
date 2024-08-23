package dnd11th.blooming.api.controller.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.support.WebMvcDescribeSpec
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import java.time.LocalDate

class MyPlantControllerValidationTest : WebMvcDescribeSpec() {
    init {
        describe("내 식물 저장") {
            context("식물종류 이름을 전달하지 않으면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            scientificName = null,
                            plantId = PLANT_ID,
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/myplants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("식물 학명은 필수값입니다."))
                    }.andDo { print() }
                }
            }

            context("식물종류 비우고 전달하면 않으면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            scientificName = " ",
                            plantId = PLANT_ID,
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/myplants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("식물 학명은 필수값입니다."))
                    }.andDo { print() }
                }
            }

            context("키우기 시작한 날짜를 미래로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            plantId = PLANT_ID,
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = FUTURE_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/myplants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("키우기 시작한 날짜는 미래일 수 없습니다."))
                    }.andDo { print() }
                }
            }
            context("마지막으로 물 준 날짜를 0~6 이외의 값으로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            plantId = PLANT_ID,
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = 7,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/myplants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("1에서 6의 값을 입력하세요."))
                    }.andDo { print() }
                }
            }
            context("마지막으로 비료 준 날짜를 0~6 이외의 값으로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            plantId = PLANT_ID,
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = 7,
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/myplants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("1에서 6의 값을 입력하세요."))
                    }.andDo { print() }
                }
            }
            context("물주기 알림 여부를 전달하지 않으면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            plantId = PLANT_ID,
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                            waterAlarm = null,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/myplants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("물주기 알림 여부는 필수값입니다."))
                    }.andDo { print() }
                }
            }
            context("비료주기 알림 여부를 전달하지 않으면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            plantId = PLANT_ID,
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = null,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/myplants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("비료주기 알림 여부는 필수값입니다."))
                    }.andDo { print() }
                }
            }
            context("건강확인 알림 여부를 전달하지 않으면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            plantId = PLANT_ID,
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = null,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/myplants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("건강확인 알림 여부는 필수값입니다."))
                    }.andDo { print() }
                }
            }
        }

        describe("내 식물 수정") {
            context("키우기 시작한 날짜를 미래로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantModifyRequest(
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = FUTURE_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.patch("/api/v1/myplants/$MYPLANT_ID") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("키우기 시작한 날짜는 미래일 수 없습니다."))
                    }.andDo { print() }
                }
            }
            context("마지막으로 물 준 날짜를 1~6 이외의 값으로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantModifyRequest(
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = 7,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.patch("/api/v1/myplants/$MYPLANT_ID") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("1에서 6의 값을 입력하세요."))
                    }.andDo { print() }
                }
            }
            context("마지막으로 비료 준 날짜를 1~6 이외의 값으로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantModifyRequest(
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = 7,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.patch("/api/v1/myplants/$MYPLANT_ID") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("1에서 6의 값을 입력하세요."))
                    }.andDo { print() }
                }
            }
        }

        describe("내 식물 알림 수정") {
            context("물주기 알림 여부를 전달하지 않으면") {
                val json =
                    objectMapper.writeValueAsString(
                        AlarmModifyRequest(
                            waterAlarm = null,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.patch("/api/v1/myplants/$MYPLANT_ID/alarm") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("물주기 알림 여부는 필수값입니다."))
                    }.andDo { print() }
                }
            }
            context("비료주기 알림 여부를 전달하지 않으면") {
                val json =
                    objectMapper.writeValueAsString(
                        AlarmModifyRequest(
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = null,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.patch("/api/v1/myplants/$MYPLANT_ID/alarm") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("비료주기 알림 여부는 필수값입니다."))
                    }.andDo { print() }
                }
            }
            context("건강확인 알림 여부를 전달하지 않으면") {
                val json =
                    objectMapper.writeValueAsString(
                        AlarmModifyRequest(
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = null,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.patch("/api/v1/myplants/$MYPLANT_ID/alarm") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("건강확인 알림 여부는 필수값입니다."))
                    }.andDo { print() }
                }
            }
        }
    }

    companion object {
        val ERROR_CODE = ErrorType.BAD_REQUEST.name
        const val PLANT_ID = 1L
        const val MYPLANT_ID = 1L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NICKNAME = "뿡뿡이"
        const val LOCATION_ID = 100L
        val FUTURE_DATE: LocalDate = LocalDate.now().plusDays(1)
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        const val LAST_WATERED_DATE = 1
        const val LAST_FERTILIZER_DATE = 2
        const val WATER_ALARM = true
        const val WATER_PERIOD = 3
        const val FERTILIZER_ALARM = false
        const val FERTILIZER_PERIOD = 30
        const val HEALTHCHECK_ALARM = true
    }
}

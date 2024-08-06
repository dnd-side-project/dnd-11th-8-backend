package dnd11th.blooming.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "1. [헬스체크]")
interface HealthApi {

    @Operation(summary = "헬스 체크 API 입니다.")
    fun healthCheck() : ResponseEntity<String>
}

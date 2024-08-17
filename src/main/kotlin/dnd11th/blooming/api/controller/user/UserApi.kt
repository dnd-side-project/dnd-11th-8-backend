package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.api.dto.user.UserRegisterRequest
import dnd11th.blooming.domain.entity.user.RegisterClaims
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "1. [유저]")
interface UserApi {
    @Operation(
        summary = "사용자 등록 API",
        parameters = [
            Parameter(
                name = "RegisterToken",
                `in` = ParameterIn.HEADER,
                required = true,
                description = "사용자 등록에 필요한 토큰",
            ),
        ],
    )
    @ApiResponse(responseCode = "200", description = "사용자 정보 등록 성공")
    fun register(
        @Schema(hidden = true) registerClaims: RegisterClaims,
        userRegisterRequest: UserRegisterRequest,
    ): TokenResponse
}

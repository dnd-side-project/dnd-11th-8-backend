package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.dto.user.MyProfileResponse
import dnd11th.blooming.api.dto.user.MyProfileUpdateRequest
import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.api.dto.user.UserRegisterRequest
import dnd11th.blooming.domain.entity.user.RegisterClaims
import dnd11th.blooming.domain.entity.user.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "2. [유저]")
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

    @Operation(summary = "마이페이지 API 입니다.")
    @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공")
    fun findProfile(
        @Schema(hidden = true) user: User,
    ): MyProfileResponse

    @Operation(summary = "닉네임 수정 API 입니다.")
    @ApiResponse(responseCode = "204", description = "닉네임 수정 성공")
    fun updateNickname(
        @Schema(hidden = true) user: User,
        updateNickName: MyProfileUpdateRequest.Nickname,
    )

    @Operation(summary = "알림 설정 수정 API 입니다.")
    @ApiResponse(responseCode = "204", description = "알람 설정 여부 변경 성공")
    fun updateAlarmStatus(
        @Schema(hidden = true) user: User,
        updateAlarmStatus: MyProfileUpdateRequest.AlarmStatus,
    )

    @Operation(summary = "알림 시간대 수정 API 입니다.")
    @ApiResponse(responseCode = "204", description = "알람 사간대 변경 성공")
    fun updateAlarmTime(
        @Schema(hidden = true) user: User,
        updateAlarmTime: MyProfileUpdateRequest.AlarmTime,
    )
}

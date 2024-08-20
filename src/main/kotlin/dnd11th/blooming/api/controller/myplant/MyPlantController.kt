package dnd11th.blooming.api.controller.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantDirectionParam
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantQueryCreteria
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.api.dto.myplant.MyPlantSaveResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSortParam
import dnd11th.blooming.api.service.myplant.MyPlantService
import dnd11th.blooming.common.annotation.LoginUser
import dnd11th.blooming.common.annotation.Secured
import dnd11th.blooming.domain.entity.user.User
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/myplants")
class MyPlantController(
    private val myPlantService: MyPlantService,
) : MyPlantApi {
    @Secured
    @PostMapping
    override fun saveMyPlant(
        @RequestBody @Valid request: MyPlantSaveRequest,
        @LoginUser user: User,
    ): MyPlantSaveResponse = myPlantService.saveMyPlant(request.toMyPlantCreateDto(), request.locationId!!, user)

    @Secured
    @GetMapping
    override fun findAllMyPlant(
        @RequestParam locationId: Long?,
        @RequestParam(defaultValue = "CREATED") sort: MyPlantSortParam,
        @RequestParam(defaultValue = "DESC") direction: MyPlantDirectionParam,
        @LoginUser user: User,
    ): List<MyPlantResponse> =
        myPlantService.findAllMyPlant(LocalDate.now(), locationId, MyPlantQueryCreteria.from(sort, direction), user)

    @Secured
    @GetMapping("/{myPlantId}")
    override fun findMyPlantDetail(
        @PathVariable myPlantId: Long,
        @LoginUser user: User,
    ): MyPlantDetailResponse = myPlantService.findMyPlantDetail(myPlantId, LocalDate.now(), user)

    @Secured
    @PatchMapping("/{myPlantId}")
    override fun modifyMyPlant(
        @PathVariable myPlantId: Long,
        @RequestBody @Valid request: MyPlantModifyRequest,
        @LoginUser user: User,
    ) = myPlantService.modifyMyPlant(myPlantId, request, user)

    @Secured
    @DeleteMapping("/{myPlantId}")
    override fun deleteMyPlant(
        @PathVariable myPlantId: Long,
        @LoginUser user: User,
    ) = myPlantService.deleteMyPlant(myPlantId, user)

    @Secured
    @PostMapping("/{myPlantId}/water")
    override fun waterMyPlant(
        @PathVariable myPlantId: Long,
        @LoginUser user: User,
    ) = myPlantService.waterMyPlant(myPlantId, LocalDate.now(), user)

    @Secured
    @PostMapping("/{myPlantId}/fertilizer")
    override fun fertilizerMyPlant(
        @PathVariable myPlantId: Long,
        @LoginUser user: User,
    ) = myPlantService.fertilizerMyPlant(myPlantId, LocalDate.now(), user)

    @Secured
    @PostMapping("/{myPlantId}/healthcheck")
    override fun healthCheckMyPlant(
        @PathVariable myPlantId: Long,
        @LoginUser user: User,
    ) = myPlantService.healthCheckMyPlant(myPlantId, LocalDate.now(), user)

    @Secured
    @PatchMapping("/{myPlantId}/alarm")
    override fun modifyMyPlantAlarm(
        @PathVariable myPlantId: Long,
        @RequestBody @Valid request: AlarmModifyRequest,
        @LoginUser user: User,
    ) = myPlantService.modifyMyPlantAlarm(myPlantId, request, user)
}

package dnd11th.blooming.api.controller.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantDirectionParam
import dnd11th.blooming.api.dto.myplant.MyPlantHealthCheckRequest
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantQueryCreteria
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.api.dto.myplant.MyPlantSaveResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSortParam
import dnd11th.blooming.api.service.myplant.MyPlantService
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
    @PostMapping
    override fun saveMyPlant(
        @RequestBody @Valid request: MyPlantSaveRequest,
    ): MyPlantSaveResponse = myPlantService.saveMyPlant(request.toMyPlantCreateDto(), request.locationId!!)

    @GetMapping
    override fun findAllMyPlant(
        @RequestParam locationId: Long?,
        @RequestParam(defaultValue = "CREATED") sort: MyPlantSortParam,
        @RequestParam(defaultValue = "DESC") direction: MyPlantDirectionParam,
    ): List<MyPlantResponse> =
        myPlantService.findAllMyPlant(LocalDate.now(), locationId, MyPlantQueryCreteria.from(sort, direction))

    @GetMapping("/{myPlantId}")
    override fun findMyPlantDetail(
        @PathVariable myPlantId: Long,
    ): MyPlantDetailResponse = myPlantService.findMyPlantDetail(myPlantId, LocalDate.now())

    @PatchMapping("/{myPlantId}")
    override fun modifyMyPlant(
        @PathVariable myPlantId: Long,
        @RequestBody @Valid request: MyPlantModifyRequest,
    ) = myPlantService.modifyMyPlant(myPlantId, request)

    @DeleteMapping("/{myPlantId}")
    override fun deleteMyPlant(
        @PathVariable myPlantId: Long,
    ) = myPlantService.deleteMyPlant(myPlantId)

    @PostMapping("/{myPlantId}/water")
    override fun waterMyPlant(
        @PathVariable myPlantId: Long,
    ) = myPlantService.waterMyPlant(myPlantId, LocalDate.now())

    @PostMapping("/{myPlantId}/fertilizer")
    override fun fertilizerMyPlant(
        @PathVariable myPlantId: Long,
    ) = myPlantService.fertilizerMyPlant(myPlantId, LocalDate.now())

    @PatchMapping("/{myPlantId}/healthcheck")
    override fun modifyMyPlantHealthCheck(
        @PathVariable myPlantId: Long,
        @RequestBody @Valid request: MyPlantHealthCheckRequest,
    ) = myPlantService.modifyMyPlantHealthCheck(myPlantId, request)

    @PatchMapping("/{myPlantId}/alarm")
    override fun modifyMyPlantAlarm(
        @PathVariable myPlantId: Long,
        @RequestBody @Valid request: AlarmModifyRequest,
    ) = myPlantService.modifyMyPlantAlarm(myPlantId, request)
}

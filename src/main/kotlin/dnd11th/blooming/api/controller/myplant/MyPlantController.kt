package dnd11th.blooming.api.controller.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantManageRequest
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.api.dto.myplant.MyPlantSaveResponse
import dnd11th.blooming.api.service.myplant.MyPlantService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/plants")
class MyPlantController(
    private val myPlantService: MyPlantService,
) {
    @PostMapping
    fun saveMyPlant(
        @RequestBody request: MyPlantSaveRequest,
    ): MyPlantSaveResponse = myPlantService.saveMyPlant(request, LocalDate.now())

    @GetMapping
    fun findAllMyPlant(): List<MyPlantResponse> = myPlantService.findAllMyPlant(LocalDate.now())

    @GetMapping("/{myPlantId}")
    fun findMyPlantDetail(
        @PathVariable myPlantId: Long,
    ): MyPlantDetailResponse = myPlantService.findMyPlantDetail(myPlantId)

    @PatchMapping("/{myPlantId}")
    fun modifyMyPlant(
        @PathVariable myPlantId: Long,
        @RequestBody request: MyPlantModifyRequest,
    ) = myPlantService.modifyMyPlant(myPlantId, request)

    @DeleteMapping("/{myPlantId}")
    fun deleteMyPlant(
        @PathVariable myPlantId: Long,
    ) = myPlantService.deleteMyPlant(myPlantId)

    @PostMapping("/{myPlantId}")
    fun manageMyPlant(
        @PathVariable myPlantId: Long,
        @RequestBody request: MyPlantManageRequest,
    ) = myPlantService.manageMyPlant(myPlantId, request, LocalDate.now())

    @PatchMapping("/{myPlantId}/alarm")
    fun modifyMyPlantAlarm(
        @PathVariable myPlantId: Long,
        @RequestBody request: AlarmModifyRequest,
    ) = myPlantService.modifyMyPlantAlarm(myPlantId, request)
}

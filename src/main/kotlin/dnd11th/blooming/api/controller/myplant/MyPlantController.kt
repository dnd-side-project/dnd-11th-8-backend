package dnd11th.blooming.api.controller.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.AlarmResponse
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
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
    fun savePlant(
        @RequestBody request: MyPlantSaveRequest,
    ) = myPlantService.savePlant(request, LocalDate.now())

    @GetMapping
    fun findAllPlant(): List<MyPlantResponse> = myPlantService.findAllPlant(LocalDate.now())

    @GetMapping("/{myPlantId}")
    fun findPlantDetail(
        @PathVariable myPlantId: Long,
    ): MyPlantDetailResponse = myPlantService.findPlantDetail(myPlantId)

    @PatchMapping("/{myPlantId}")
    fun modifyMyPlant(
        @PathVariable myPlantId: Long,
        @RequestBody request: MyPlantModifyRequest,
    ) = myPlantService.modifyMyPlant(myPlantId, request)

    @DeleteMapping("/{myPlantId}")
    fun deleteMyPlant(
        @PathVariable myPlantId: Long,
    ) = myPlantService.deleteMyPlant(myPlantId)

    @GetMapping("/{myPlantId}/alarm")
    fun findPlantAlarm(
        @PathVariable myPlantId: Long,
    ): AlarmResponse = myPlantService.findPlantAlarm(myPlantId)

    @PatchMapping("/{myPlantId}/alarm")
    fun modifyPlantAlarm(
        @PathVariable myPlantId: Long,
        @RequestBody request: AlarmModifyRequest,
    ) = myPlantService.modifyPlantAlarm(myPlantId, request)
}

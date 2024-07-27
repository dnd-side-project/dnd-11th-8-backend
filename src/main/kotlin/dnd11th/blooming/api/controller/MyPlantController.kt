package dnd11th.blooming.api.controller

import dnd11th.blooming.api.dto.AlarmModifyRequest
import dnd11th.blooming.api.dto.AlarmResponse
import dnd11th.blooming.api.dto.MyPlantDetailResponse
import dnd11th.blooming.api.dto.MyPlantResponse
import dnd11th.blooming.api.dto.MyPlantSaveRequest
import dnd11th.blooming.api.dto.MyPlantSaveResponse
import dnd11th.blooming.api.service.MyPlantService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/plants")
class MyPlantController(
    private val myPlantService: MyPlantService,
) {
    @PostMapping
    fun savePlant(
        @RequestBody request: MyPlantSaveRequest,
    ): MyPlantSaveResponse = myPlantService.savePlant(request)

    @GetMapping
    fun findAllPlant(): List<MyPlantResponse> = myPlantService.findAllPlant()

    @GetMapping("/{myPlantId}")
    fun findPlantDetail(
        @PathVariable myPlantId: Long,
    ): MyPlantDetailResponse = myPlantService.findPlantDetail(myPlantId)

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

package dnd11th.blooming.api.controller

import dnd11th.blooming.api.dto.MyPlantDetailResponse
import dnd11th.blooming.api.dto.MyPlantResponse
import dnd11th.blooming.api.dto.MyPlantSaveRequest
import dnd11th.blooming.api.dto.MyPlantSaveResponse
import dnd11th.blooming.api.service.MyPlantService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MyPlantController(
    private val myPlantService: MyPlantService,
) {
    @PostMapping("/plant")
    fun createPlant(
        @RequestBody request: MyPlantSaveRequest,
    ): MyPlantSaveResponse = myPlantService.savePlant(request)

    @GetMapping("/plants")
    fun getAllPlant(): List<MyPlantResponse> = myPlantService.findAllPlant()

    @GetMapping("/plant/{id}")
    fun getPlantDetail(
        @PathVariable id: Long,
    ): MyPlantDetailResponse = myPlantService.findPlantDetail(id)
}

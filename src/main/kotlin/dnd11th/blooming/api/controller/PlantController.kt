package dnd11th.blooming.api.controller

import dnd11th.blooming.api.dto.PlantResponse
import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.dto.PlantSaveResponse
import dnd11th.blooming.api.service.PlantService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PlantController(
    private val plantService: PlantService,
) {
    @PostMapping("/plant")
    fun createPlant(
        @RequestBody request: PlantSaveRequest,
    ): PlantSaveResponse = plantService.savePlant(request)

    @GetMapping("/plants")
    fun getAllPlant(): List<PlantResponse> = plantService.findAllPlant()
}

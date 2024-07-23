package dnd11th.blooming.api

import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.dto.PlantSaveResponse
import dnd11th.blooming.service.PlantService
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
}

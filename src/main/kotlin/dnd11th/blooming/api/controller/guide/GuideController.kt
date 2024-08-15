package dnd11th.blooming.api.controller.guide

import dnd11th.blooming.api.dto.guide.PlantGuideResponse
import dnd11th.blooming.api.dto.guide.PlantResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/plants")
class GuideController : GuideApi {
    @PostMapping
    override fun findPlant(
        @RequestParam plantName: String,
    ): List<PlantResponse> {
        TODO("Not yet implemented")
    }

    @GetMapping("/{guideId}")
    override fun findPlantGuide(
        @PathVariable guideId: Long,
    ): PlantGuideResponse {
        TODO("Not yet implemented")
    }
}

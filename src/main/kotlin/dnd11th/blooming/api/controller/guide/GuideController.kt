package dnd11th.blooming.api.controller.guide

import dnd11th.blooming.api.dto.guide.PlantGuideResponse
import dnd11th.blooming.api.dto.guide.PlantResponse
import dnd11th.blooming.api.service.guide.GuideService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/plants")
class GuideController(
    private val guideService: GuideService,
) : GuideApi {
    @PostMapping
    override fun findPlantList(
        @RequestParam plantName: String,
    ): List<PlantResponse> = guideService.findPlantList(plantName)

    @GetMapping("/{plantId}")
    override fun findPlantGuide(
        @PathVariable plantId: Long,
    ): PlantGuideResponse = guideService.findPlantGuide(plantId, LocalDate.now().month)
}

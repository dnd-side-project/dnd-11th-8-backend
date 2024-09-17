package dnd11th.blooming.api.controller.region

import dnd11th.blooming.api.dto.region.RegionResponse
import dnd11th.blooming.api.service.region.RegionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/region")
class RegionController(
    private val regionService: RegionService,
) : RegionApi {
    @GetMapping
    override fun findRegion(
        @RequestParam name: String,
    ): List<RegionResponse> {
        return regionService.findRegion(name)
    }
}

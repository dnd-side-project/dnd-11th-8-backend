package dnd11th.blooming.api.controller.onboard

import dnd11th.blooming.api.dto.onboard.OnboardResultRequest
import dnd11th.blooming.api.dto.onboard.OnboardResultResponse
import dnd11th.blooming.api.dto.onboard.OnboardScriptResponse
import dnd11th.blooming.api.service.onboard.OnboardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/onboarding")
class OnboardController(
    private val onboardService: OnboardService,
) : OnboardApi {
    @GetMapping
    override fun findScripts(): OnboardScriptResponse = onboardService.findScripts()

    @PostMapping
    override fun submitScripts(
        @RequestParam version: Int,
        @RequestBody request: List<OnboardResultRequest>,
    ): OnboardResultResponse = onboardService.submitScripts(version, request)
}

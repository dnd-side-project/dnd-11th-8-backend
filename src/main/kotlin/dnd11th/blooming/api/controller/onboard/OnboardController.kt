package dnd11th.blooming.api.controller.onboard

import dnd11th.blooming.api.dto.onboard.ScriptResponse
import dnd11th.blooming.api.service.onboard.OnboardService
import dnd11th.blooming.common.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/onboarding")
class OnboardController(
    private val onboardService: OnboardService,
) : OnboardApi {
    @Secured
    @GetMapping
    override fun findScripts(): List<ScriptResponse> = onboardService.findScripts()
}

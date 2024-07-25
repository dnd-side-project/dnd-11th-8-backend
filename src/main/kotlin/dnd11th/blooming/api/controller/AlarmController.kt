package dnd11th.blooming.api.controller

import dnd11th.blooming.api.service.AlarmService
import org.springframework.web.bind.annotation.RestController

@RestController
class AlarmController(
    private val alarmService: AlarmService,
)

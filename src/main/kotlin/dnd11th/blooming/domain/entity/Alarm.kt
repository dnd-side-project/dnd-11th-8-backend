package dnd11th.blooming.domain.entity

import dnd11th.blooming.api.dto.myplant.AlarmModifyDto
import dnd11th.blooming.api.dto.myplant.MyPlantCreateDto
import dnd11th.blooming.domain.entity.plant.Fertilizer
import dnd11th.blooming.domain.entity.plant.Plant
import dnd11th.blooming.domain.entity.plant.Water
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.Month

@Embeddable
class Alarm(
    @Column(nullable = false)
    var waterAlarm: Boolean,
    @Column(nullable = false)
    var waterPeriod: Int,
    @Column(nullable = false)
    var fertilizerAlarm: Boolean,
    @Column(nullable = false)
    var fertilizerPeriod: Int,
    @Column(nullable = false)
    var healthCheckAlarm: Boolean,
) {
    companion object {
        fun createAlarm(
            dto: AlarmModifyDto,
            plant: Plant?,
            month: Month,
        ): Alarm = createAlarm(dto.waterAlarm, dto.waterPeriod, dto.fertilizerAlarm, dto.fertilizerPeriod, dto.healthCheckAlarm, plant, month)

        fun createAlarm(
            dto: MyPlantCreateDto,
            plant: Plant?,
            month: Month,
        ): Alarm = createAlarm(dto.waterAlarm, dto.waterPeriod, dto.fertilizerAlarm, dto.fertilizerPeriod, dto.healthCheckAlarm, plant, month)

        private fun createAlarm(
            waterAlarm: Boolean,
            waterPeriod: Int?,
            fertilizerAlarm: Boolean,
            fertilizerPeriod: Int?,
            healthCheckAlarm: Boolean,
            plant: Plant?,
            month: Month,
        ): Alarm =
            Alarm(
                waterAlarm = waterAlarm,
                waterPeriod = waterPeriod ?: plant?.getRecommendWaterDayPeriod(month) ?: Water.MOIST.periodDay,
                fertilizerAlarm = fertilizerAlarm,
                fertilizerPeriod = fertilizerPeriod ?: plant?.getRecommendFertilizerDayPeriod() ?: (Fertilizer.DEMAND.periodWeek * 7),
                healthCheckAlarm = healthCheckAlarm,
            )
    }
}

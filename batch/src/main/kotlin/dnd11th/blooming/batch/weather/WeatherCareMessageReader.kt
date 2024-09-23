package dnd11th.blooming.batch.weather

import dnd11th.blooming.core.entity.region.Region
import dnd11th.blooming.core.repository.region.RegionRepository
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WeatherCareMessageReader(
    private val regionRepository: RegionRepository
) {
    @Bean
    @StepScope
    fun weatherCareMessageItemReader(): ListItemReader<Region> {
        val regions: List<Region> = regionRepository.findAll()
        return ListItemReader(regions)
    }
}

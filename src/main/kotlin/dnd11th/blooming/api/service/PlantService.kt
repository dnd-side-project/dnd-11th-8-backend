package dnd11th.blooming.api.service

import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.dto.PlantSaveResponse
import dnd11th.blooming.service.implement.PlantWriter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PlantService(
    private val plantWriter: PlantWriter,
) {
    fun savePlant(request: PlantSaveRequest): PlantSaveResponse {
        return plantWriter.saveOne(request)
    }
}

package dnd11th.blooming.api.service.image

import dnd11th.blooming.api.dto.image.ImageFavoriteModifyRequest
import dnd11th.blooming.api.dto.image.ImageSaveRequest
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.repository.ImageRepository
import dnd11th.blooming.domain.repository.MyPlantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ImageService(
    private val myPlantRepository: MyPlantRepository,
    private val imageRepository: ImageRepository,
) {
    @Transactional
    fun saveImage(
        myPlantId: Long,
        request: ImageSaveRequest,
        now: LocalDate,
    ) {
        val myPlant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        val image =
            request.toImage(now).also {
                it.makeMyPlantRelation(myPlant)
            }

        imageRepository.save(image)
    }

    @Transactional
    fun modifyFavorite(
        imageId: Long,
        request: ImageFavoriteModifyRequest,
    ) {
        val image = imageRepository.findByIdOrNull(imageId) ?: throw NotFoundException(ErrorType.NOT_FOUND_IMAGE)

        image.modifyFavorite(request.favorite)
    }
}

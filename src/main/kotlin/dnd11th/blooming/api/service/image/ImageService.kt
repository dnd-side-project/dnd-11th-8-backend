package dnd11th.blooming.api.service.image

import dnd11th.blooming.api.dto.image.ImageCreateDto
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Image
import dnd11th.blooming.domain.repository.ImageRepository
import dnd11th.blooming.domain.repository.MyPlantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ImageService(
    private val myPlantRepository: MyPlantRepository,
    private val imageRepository: ImageRepository,
) {
    @Transactional
    fun saveImage(
        myPlantId: Long,
        dto: ImageCreateDto,
    ) {
        val myPlant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        val image = Image.createImage(dto, myPlant)

        imageRepository.save(image)
    }

    @Transactional
    fun modifyFavorite(
        imageId: Long,
        favorite: Boolean,
    ) {
        val image =
            imageRepository.findByIdOrNull(imageId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_IMAGE)

        image.modifyFavorite(favorite)
    }

    @Transactional
    fun deleteImage(imageId: Long) {
        if (imageRepository.existsById(imageId)) {
            throw NotFoundException(ErrorType.NOT_FOUND_IMAGE)
        }

        imageRepository.deleteById(imageId)
    }
}

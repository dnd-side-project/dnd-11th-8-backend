package dnd11th.blooming.api.service.image

import dnd11th.blooming.api.dto.image.ImageCreateDto
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.core.entity.image.Image
import dnd11th.blooming.domain.core.entity.user.User
import dnd11th.blooming.domain.core.repository.image.ImageRepository
import dnd11th.blooming.domain.core.repository.myplant.MyPlantRepository
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
        user: User,
    ) {
        val myPlant =
            myPlantRepository.findByIdAndUser(myPlantId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        val image = Image.createImage(dto, myPlant)

        myPlant.plantImageUrl = image.url

        imageRepository.save(image)
    }

    @Transactional
    fun modifyFavorite(
        imageId: Long,
        favorite: Boolean,
        user: User,
    ) {
        val image =
            imageRepository.findByIdAndUser(imageId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_IMAGE)

        image.modifyFavorite(favorite)
    }

    @Transactional
    fun deleteImage(
        imageId: Long,
        user: User,
    ) {
        val image =
            imageRepository.findByIdAndUser(imageId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_IMAGE)

        imageRepository.delete(image)
    }
}

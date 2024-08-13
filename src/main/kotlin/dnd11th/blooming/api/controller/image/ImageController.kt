package dnd11th.blooming.api.controller.image

import dnd11th.blooming.api.dto.image.ImageFavoriteModifyRequest
import dnd11th.blooming.api.dto.image.ImageSaveRequest
import dnd11th.blooming.api.service.image.ImageService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/myplants")
class ImageController(
    private val imageService: ImageService,
) : ImageApi {
    @PostMapping("/{myPlantId}/image")
    override fun saveImage(
        @PathVariable myPlantId: Long,
        @RequestBody @Valid request: ImageSaveRequest,
    ) = imageService.saveImage(myPlantId, request, LocalDate.now())

    @PatchMapping("/image/{imageId}")
    override fun modifyFavorite(
        @PathVariable imageId: Long,
        @RequestBody @Valid request: ImageFavoriteModifyRequest,
    ) {
        println(">>>" + request.favorite)
        imageService.modifyFavorite(imageId, request)
    }

    @DeleteMapping("/image/{imageId}")
    override fun deleteImage(
        @PathVariable imageId: Long,
    ) = imageService.deleteImage(imageId)
}

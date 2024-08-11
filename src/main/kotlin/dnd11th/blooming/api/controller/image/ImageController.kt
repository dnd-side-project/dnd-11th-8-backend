package dnd11th.blooming.api.controller.image

import dnd11th.blooming.api.dto.image.ImageFavoriteModifyRequest
import dnd11th.blooming.api.dto.image.ImageSaveRequest
import dnd11th.blooming.api.service.image.ImageService
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
) {
    @PostMapping("/{myPlantId}/image")
    fun saveImage(
        @PathVariable myPlantId: Long,
        @RequestBody request: ImageSaveRequest,
    ) = imageService.saveImage(myPlantId, request, LocalDate.now())

    @PatchMapping("/image/{imageId}")
    fun modifyFavorite(
        @PathVariable imageId: Long,
        @RequestBody request: ImageFavoriteModifyRequest,
    ) = imageService.modifyFavorite(imageId, request)
}

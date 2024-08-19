package dnd11th.blooming.api.controller.image

import dnd11th.blooming.api.dto.image.ImageFavoriteModifyRequest
import dnd11th.blooming.api.dto.image.ImageSaveRequest
import dnd11th.blooming.api.service.image.ImageService
import dnd11th.blooming.common.annotation.LoginUser
import dnd11th.blooming.common.annotation.Secured
import dnd11th.blooming.domain.entity.user.User
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/myplants")
class ImageController(
    private val imageService: ImageService,
) : ImageApi {
    @Secured
    @PostMapping("/{myPlantId}/image")
    override fun saveImage(
        @PathVariable myPlantId: Long,
        @RequestBody @Valid request: ImageSaveRequest,
        @LoginUser user: User,
    ) = imageService.saveImage(myPlantId, request.toImageCreateDto(), user)

    @Secured
    @PatchMapping("/image/{imageId}")
    override fun modifyFavorite(
        @PathVariable imageId: Long,
        @RequestBody @Valid request: ImageFavoriteModifyRequest,
        @LoginUser user: User,
    ) = imageService.modifyFavorite(imageId, request.favorite!!, user)

    @Secured
    @DeleteMapping("/image/{imageId}")
    override fun deleteImage(
        @PathVariable imageId: Long,
        @LoginUser user: User,
    ) = imageService.deleteImage(imageId, user)
}

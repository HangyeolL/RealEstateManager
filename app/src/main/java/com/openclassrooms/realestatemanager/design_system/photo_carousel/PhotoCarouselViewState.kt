package com.openclassrooms.realestatemanager.design_system.photo_carousel

sealed class PhotoCarouselViewState(
    val type: PhotoCarouselType,
) {
    data class Content(
        val photoId: Int,
        val photoUrl: String,
        val photoDescription: String?,
    ):PhotoCarouselViewState(PhotoCarouselType.CONTENT)

    data class AddPhoto(
        val onClick: () -> Unit,
    ): PhotoCarouselViewState(PhotoCarouselType.ADD_PHOTO)

    enum class PhotoCarouselType {
        CONTENT,
        ADD_PHOTO
    }
}
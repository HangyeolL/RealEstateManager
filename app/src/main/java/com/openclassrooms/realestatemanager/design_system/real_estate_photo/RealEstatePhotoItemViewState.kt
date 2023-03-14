package com.openclassrooms.realestatemanager.design_system.real_estate_photo

sealed class RealEstatePhotoItemViewState(
    val type: PhotoCarouselType,
) {
    data class Content(
        val photoId: Int,
        val photoUrl: String,
        val photoDescription: String?,
    ):RealEstatePhotoItemViewState(PhotoCarouselType.CONTENT)

    data class AddRealEstatePhoto(
        val onClick: () -> Unit,
    ): RealEstatePhotoItemViewState(PhotoCarouselType.ADD_PHOTO)

    enum class PhotoCarouselType {
        CONTENT,
        ADD_PHOTO
    }
}
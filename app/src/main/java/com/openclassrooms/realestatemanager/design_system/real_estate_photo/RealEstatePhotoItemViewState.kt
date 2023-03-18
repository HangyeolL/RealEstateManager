package com.openclassrooms.realestatemanager.design_system.real_estate_photo

sealed class RealEstatePhotoItemViewState(
    val type: RealEstatePhotoType,
) {
    data class Content(
        val photoId: Int,
        val photoUrl: String,
        val photoDescription: String?,
    ):RealEstatePhotoItemViewState(RealEstatePhotoType.CONTENT)

    data class AddRealEstatePhoto(
        val onClick: () -> Unit,
    ): RealEstatePhotoItemViewState(RealEstatePhotoType.ADD_PHOTO)

    enum class RealEstatePhotoType {
        CONTENT,
        ADD_PHOTO
    }
}
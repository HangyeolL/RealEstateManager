package com.openclassrooms.realestatemanager.design_system.real_estate_photo

sealed class RealEstatePhotoItemViewState(
    val type: RealEstatePhotoType,
) {
    data class Content(
        val photoId: Int,
        val photoUrl: String,
        val photoDescription: String?,
    ):RealEstatePhotoItemViewState(RealEstatePhotoType.CONTENT)

    object AddRealEstatePhoto: RealEstatePhotoItemViewState(RealEstatePhotoType.ADD_PHOTO)

    enum class RealEstatePhotoType {
        CONTENT,
        ADD_PHOTO
    }
}
package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

// TODO ViewState is same as DetailListItemViewState !! Even Adapter and Layout files are the same !
@Deprecated("Use PhotoCarouselViewState instead", replaceWith = ReplaceWith("PhotoCarouselViewState"))
data class AddOrModifyRealEstatePhotoListItemViewState(
    val photoId: Int,
    val photoUrl: String,
    val photoDescription: String?,
)
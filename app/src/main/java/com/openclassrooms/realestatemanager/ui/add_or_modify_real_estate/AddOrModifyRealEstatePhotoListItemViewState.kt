package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

// TODO ViewState is same as DetailListItemViewState !! Even Adapter and Layout files are the same !
data class AddOrModifyRealEstatePhotoListItemViewState(
    val photoId: Int,
    val photoUrl: String,
    val photoDescription: String?,
)
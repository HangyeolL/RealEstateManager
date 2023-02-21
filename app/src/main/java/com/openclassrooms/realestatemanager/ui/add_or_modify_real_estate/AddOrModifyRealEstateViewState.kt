package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

data class AddOrModifyRealEstateViewState(

    val typeSpinnerItemViewStateList: List<AddOrModifyRealEstateTypeSpinnerItemViewState>,
    val agentSpinnerItemViewStateList: List<AddOrModifyRealEstateAgentSpinnerItemViewState>,
    val realEstatePhotoListItemViewStateList: List<AddOrModifyRealEstatePhotoListItemViewState>,
    val address: String,
    val numberOfRooms: Int,
    val numberOfBathrooms: Int,
    val numberOfBedrooms: Int,
    val squareMeter: Int,
    val marketSince: String,
    val price: Int,
    val garage: Boolean,
    val guard: Boolean,
    val garden: Boolean,
    val elevator: Boolean,
    val groceryStoreNearby: Boolean,
    val isSoldOut: Boolean,
    val dateOfSold: String?,
    val description: String,
    )


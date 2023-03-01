package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.PredictionResponse
import com.openclassrooms.realestatemanager.design_system.photo_carousel.RealEstatePhotoItemViewState

data class AddOrModifyRealEstateViewState(
    val typeSpinnerItemViewStateList: List<AddOrModifyRealEstateTypeSpinnerItemViewState>,
    val agentSpinnerItemViewStateList: List<AddOrModifyRealEstateAgentSpinnerItemViewState>,
    val realEstatePhotoListItemViewStateList: List<RealEstatePhotoItemViewState>,
    val address: String,
    val numberOfRooms: String,
    val numberOfBathrooms: String,
    val numberOfBedrooms: String,
    val squareMeter: String,
    val marketSince: String,
    val price: String,
    val garage: Boolean,
    val guard: Boolean,
    val garden: Boolean,
    val elevator: Boolean,
    val groceryStoreNearby: Boolean,
    val isSoldOut: Boolean,
    val dateOfSold: String?,
    val description: String,
)


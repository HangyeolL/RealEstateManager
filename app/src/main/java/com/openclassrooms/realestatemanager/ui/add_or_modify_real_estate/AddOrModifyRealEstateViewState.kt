package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import com.openclassrooms.realestatemanager.design_system.real_estate_agent.RealEstateAgentSpinnerItemViewState
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.design_system.real_estate_type.RealEstateTypeSpinnerItemViewState

sealed class AddOrModifyRealEstateViewState(
    val type: AddOrModifyRealEstateViewStateType,
) {
    data class Modification(
        val typeSpinnerItemViewStateList: List<RealEstateTypeSpinnerItemViewState>,
        val agentSpinnerItemViewStateList: List<RealEstateAgentSpinnerItemViewState>,
        val realEstatePhotoListItemViewStateList: List<RealEstatePhotoItemViewState>,
        val address: String,
        val city: String,
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
    ) : AddOrModifyRealEstateViewState(AddOrModifyRealEstateViewStateType.CREATING_CASE)

    data class Creation(
        val typeSpinnerItemViewStateList: List<RealEstateTypeSpinnerItemViewState>,
        val agentSpinnerItemViewStateList: List<RealEstateAgentSpinnerItemViewState>,
        val realEstatePhotoListItemViewStateList: List<RealEstatePhotoItemViewState>,
    ) : AddOrModifyRealEstateViewState(AddOrModifyRealEstateViewStateType.MODIFYING_CASE)

    enum class AddOrModifyRealEstateViewStateType {
        MODIFYING_CASE,
        CREATING_CASE
    }
}


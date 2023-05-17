package com.openclassrooms.realestatemanager.ui.search

import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateAgentSpinnerItemViewState
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateTypeSpinnerItemViewState

sealed class SearchViewState(
    val type: SearchViewStateType,
) {
    data class InitialContent(
        val typeSpinnerItemViewStateList: List<AddOrModifyRealEstateTypeSpinnerItemViewState>,
        val agentSpinnerItemViewStateList: List<AddOrModifyRealEstateAgentSpinnerItemViewState>,
    ) : SearchViewState(SearchViewStateType.INITIAL)

    data class WithUserInput(
        val numberOfBathrooms: String,
        val numberOfBedrooms: String,
        val minSquareMeter: String,
        val maxSquareMeter: String,
        val minPrice: String,
        val maxPrice: String,

        val garage: Boolean,
        val guard: Boolean,
        val garden: Boolean,
        val elevator: Boolean,
        val groceryStoreNearby: Boolean,
        val soldOutRecently: Boolean,
        val registeredRecently: Boolean,
        val photoAvailable: Boolean,
    ) : SearchViewState(SearchViewStateType.USER_INPUT)

    enum class SearchViewStateType {
        INITIAL,
        USER_INPUT
    }
}



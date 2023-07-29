package com.openclassrooms.realestatemanager.ui.search

import com.openclassrooms.realestatemanager.design_system.real_estate_agent.RealEstateAgentSpinnerItemViewState
import com.openclassrooms.realestatemanager.design_system.real_estate_type.RealEstateTypeSpinnerItemViewState

sealed class SearchViewState(
    val type: SearchViewStateType,
) {
    data class InitialContent(
        val typeSpinnerItemViewStateList: List<RealEstateTypeSpinnerItemViewState>,
        val agentSpinnerItemViewStateList: List<RealEstateAgentSpinnerItemViewState>,
    ) : SearchViewState(SearchViewStateType.INITIAL)

//    data class WithUserInput(
//        val numberOfBathrooms: String,
//        val numberOfBedrooms: String,
//        val minSquareMeter: String,
//        val maxSquareMeter: String,
//        val minPrice: String,
//        val maxPrice: String,
//
//        val garage: Boolean,
//        val guard: Boolean,
//        val garden: Boolean,
//        val elevator: Boolean,
//        val groceryStoreNearby: Boolean,
//        val soldOutRecently: Boolean,
//        val registeredRecently: Boolean,
//        val photoAvailable: Boolean,
//    ) : SearchViewState(SearchViewStateType.USER_INPUT)

    enum class SearchViewStateType {
        INITIAL,
        USER_INPUT
    }
}



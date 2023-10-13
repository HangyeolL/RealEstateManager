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

    enum class SearchViewStateType {
        INITIAL,
    }
}



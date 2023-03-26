package com.openclassrooms.realestatemanager.ui.real_estate_list

sealed class RealEstateListViewAction {
    data class NavigateToAddOrModifyRealEstateActivity(val realEstateId: Int?) : RealEstateListViewAction()
}

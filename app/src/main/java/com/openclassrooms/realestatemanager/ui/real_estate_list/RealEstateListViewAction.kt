package com.openclassrooms.realestatemanager.ui.real_estate_list

import com.openclassrooms.realestatemanager.ui.main.MainViewAction

sealed class RealEstateListViewAction {
    data class NavigateToAddOrModifyRealEstateActivity(val realEstateId: Int?) : RealEstateListViewAction()
}

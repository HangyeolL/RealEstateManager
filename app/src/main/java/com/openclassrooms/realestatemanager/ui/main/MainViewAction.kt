package com.openclassrooms.realestatemanager.ui.main

sealed class MainViewAction {

    object NavigateToDetailActivity : MainViewAction()

    data class NavigateToAddOrModifyRealEstateActivity(val realEstateId: Int?) : MainViewAction()
}

package com.openclassrooms.realestatemanager.ui.main

sealed class MainViewAction {
    object NavigateToDetailActivity : MainViewAction()
    data class NavigateToAddRealEstateActivity(val realEstateId: Int?) : MainViewAction()
}

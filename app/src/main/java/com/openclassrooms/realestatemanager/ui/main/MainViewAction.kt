package com.openclassrooms.realestatemanager.ui.main
//TODO Should I create  ViewAction for every activity class ?
sealed class MainViewAction {
    object NavigateToDetailActivity : MainViewAction()
    data class NavigateToAddOrModifyRealEstateActivity(val realEstateId: Int?) : MainViewAction()
}

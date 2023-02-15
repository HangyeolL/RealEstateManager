package com.openclassrooms.realestatemanager.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currentRealEstateRepository: CurrentRealEstateRepository
) : ViewModel() {

    private var isTablet: Boolean = false

    val viewActionSingleLiveEvent: SingleLiveEvent<MainViewAction> = SingleLiveEvent()

    init {
        // TODO Need to improve Navigation part here
//        1. doing navigation via RV adapter by passing viewState Id when opening DetailActivity -> Should I unit test RV ?
        viewActionSingleLiveEvent.addSource(
            currentRealEstateRepository.getCurrentRealEstateIdStateFlow()
                .filterNotNull()
                .asLiveData()
        ) {
            if (!isTablet) {
                viewActionSingleLiveEvent.setValue(MainViewAction.NavigateToDetailActivity)
            }
        }
    }

    fun onResume(isTablet: Boolean) {
        this.isTablet = isTablet
    }

    fun onToolBarMenuCreateClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            val realEstateId = currentRealEstateRepository.getCurrentRealEstateIdStateFlow().firstOrNull()
            withContext(Dispatchers.Main) {
                viewActionSingleLiveEvent.setValue(
                    MainViewAction.NavigateToAddRealEstateActivity(realEstateId)
                )
            }
        }
    }

}
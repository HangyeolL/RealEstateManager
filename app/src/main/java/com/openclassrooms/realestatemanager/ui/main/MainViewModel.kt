package com.openclassrooms.realestatemanager.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    currentRealEstateRepository: CurrentRealEstateRepository
) : ViewModel() {

    private var isTablet: Boolean = false

    val viewActionSingleLiveEvent: SingleLiveEvent<MainViewAction> = SingleLiveEvent()

    init {
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

}
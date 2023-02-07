package com.openclassrooms.realestatemanager.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currentRealEstateRepository: CurrentRealEstateRepository
) : ViewModel() {

    private var isTablet: Boolean = false

    val viewActionSingleLiveEvent: SingleLiveEvent<MainViewAction> = SingleLiveEvent()

    init {
        viewActionSingleLiveEvent.addSource(currentRealEstateRepository.getCurrentRealEstateId().asLiveData()) {
            if (it != null) {
                if (!isTablet) {
                    viewActionSingleLiveEvent.setValue(MainViewAction.NavigateToDetailActivity)
                }
            }
        }
    }

    fun onResume(isTablet: Boolean) {
        this.isTablet = isTablet
    }

}
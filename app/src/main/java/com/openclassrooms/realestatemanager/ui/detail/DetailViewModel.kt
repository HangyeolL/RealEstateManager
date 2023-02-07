package com.openclassrooms.realestatemanager.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    private val currentRealEstateRepository: CurrentRealEstateRepository,
) : ViewModel() {

    private val _detailViewState = MutableStateFlow<DetailViewState?>(null)
    val detailViewState = _detailViewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {

            currentRealEstateRepository.getCurrentRealEstateId().collect() { currentRealEstateId ->
                if(currentRealEstateId != null) {
                    val realEstate = realEstateRepository.getRealEstateById(currentRealEstateId)

                    realEstate.collect() {
                        _detailViewState.value =  DetailViewState(
                            it.descriptionBody,
                            it.squareMeter,
                            it.numberOfRooms,
                            it.numberOfBathrooms,
                            it.numberOfBedrooms,
                            it.address,
                            it.latLng,
                            "Agent Name",
                            true
                        )
                    }
                }
            }


        }
    }




}
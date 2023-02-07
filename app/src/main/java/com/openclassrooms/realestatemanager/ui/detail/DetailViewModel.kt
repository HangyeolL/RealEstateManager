package com.openclassrooms.realestatemanager.ui.detail

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
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

    val detailViewState: LiveData<DetailViewState> = liveData(Dispatchers.IO) {
        currentRealEstateRepository.getCurrentRealEstateId()
            .filterNotNull()
            .flatMapLatest { currentRealEstateId ->
                realEstateRepository.getRealEstateById(currentRealEstateId)
            }.collect {
                emit(
                    DetailViewState(
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
                )
            }
    }
}
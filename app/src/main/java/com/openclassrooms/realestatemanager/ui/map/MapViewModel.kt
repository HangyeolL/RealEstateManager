package com.openclassrooms.realestatemanager.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MapViewModel @Inject constructor(
    currentRealEstateRepository: CurrentRealEstateRepository,
    realEstateRepository: RealEstateRepository
) : ViewModel() {

    val latLngLiveData: LiveData<MapViewState> = liveData(Dispatchers.IO) {
        currentRealEstateRepository.getCurrentRealEstateIdStateFlow()
            .filterNotNull()
            .flatMapLatest { currentRealEstateId ->
                realEstateRepository.getRealEstateById(currentRealEstateId)
            }
            .collectLatest { realEstateWithPhotos ->
                emit(
                    MapViewState(
                        realEstateWithPhotos.realEstateEntity.latLng
                    )
                )
            }
    }


}


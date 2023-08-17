package com.openclassrooms.realestatemanager.ui.map_view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.domain.CoroutineDispatcherProvider
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import com.openclassrooms.realestatemanager.domain.realestate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realestate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val currentRealEstateRepository: CurrentRealEstateRepository,
    realEstateRepository: RealEstateRepository,
    locationRepository: LocationRepository,
) : ViewModel() {

    companion object {
        private const val DEFAULT_LATITUDE = 48.85648195552058
        private const val DEFAULT_LONGITUDE = 2.352610864067402
    }

    private val allRealEstatesFlow = realEstateRepository.getAllRealEstates()
    private val userLocationStateFlow = locationRepository.getLocationStateFlow()

    val viewStateLiveData = liveData(coroutineDispatcherProvider.io) {
        coroutineScope {

            val allRealEstatesListAsync = async { allRealEstatesFlow.first() }.await()

            val mapMarkerViewStateList = allRealEstatesListAsync.map { realEstateEntity ->
                MapMarkerViewState(
                    realEstateAddress = realEstateEntity.address,
                    realEstateLatLng = realEstateEntity.latLng,
                    selectedRealEstateId = realEstateEntity.realEstateId,
                )
            }

            emit(
                MapViewState(
                    mapMarkerViewStateList = mapMarkerViewStateList,
                )
            )
        }
    }

    val userLocationViewStateLiveData = liveData(coroutineDispatcherProvider.io) {
        userLocationStateFlow.collectLatest { location ->
            if (location != null) {
                emit(
                    UserLocationViewState(
                        LatLng(location.latitude, location.longitude)
                    )
                )
            } else {
                emit(
                    UserLocationViewState(
                        LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
                    )
                )
            }

        }

    }

    fun onMarkerInfoWindowClicked(selectedRealEstateId: Int, onFinished: () -> Unit) {
        currentRealEstateRepository.setCurrentRealEstateId(selectedRealEstateId)
        onFinished()
    }

}
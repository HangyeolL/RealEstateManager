package com.openclassrooms.realestatemanager.ui.map_view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val currentRealEstateRepository: CurrentRealEstateRepository,
    private val realEstateRepository: RealEstateRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {

    companion object {
        private const val DEFAULT_LATITUDE = 48.85648195552058
        private const val DEFAULT_LONGITUDE = 2.352610864067402
    }

    private val allRealEstatesFlow = realEstateRepository.getAllRealEstates()
    private val userLocationStateFlow = locationRepository.getLocationStateFlow()

    val viewStateLiveData = liveData(Dispatchers.IO) {
        combine(
            allRealEstatesFlow,
            userLocationStateFlow,
        ) { allRealEstates, userLocation ->
//            val mapMarkerViewStateList = ArrayList<MapMarkerViewState>()
//
//            allRealEstates.forEach { realEstateEntity ->
//                mapMarkerViewStateList.add(
//                    MapMarkerViewState(
//                        realEstateAddress = realEstateEntity.address,
//                        realEstateLatLng = realEstateEntity.latLng,
//                        selectedRealEstateId = realEstateEntity.realEstateId,
//                    )
//                )
//            }
            emit(
                MapViewState(
                    mapMarkerViewStateList = allRealEstates.map { realEstateEntity ->
                        MapMarkerViewState(
                            realEstateAddress = realEstateEntity.address,
                            realEstateLatLng = realEstateEntity.latLng,
                            selectedRealEstateId = realEstateEntity.realEstateId,
                        )
                    },
                    userLocationLatLng = LatLng(
                        userLocation?.latitude ?: DEFAULT_LATITUDE,
                        userLocation?.longitude ?: DEFAULT_LONGITUDE
                    ),
                )
            )
        }.collectLatest { }
    }

    fun onMarkerInfoWindowClicked(selectedRealEstateId: Int, onFinished : () -> Unit) {
        currentRealEstateRepository.setCurrentRealEstateId(selectedRealEstateId)
        onFinished()
    }

}
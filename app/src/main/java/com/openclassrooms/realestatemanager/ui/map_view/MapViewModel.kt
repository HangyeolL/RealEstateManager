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
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    realEstateRepository: RealEstateRepository,
    locationRepository: LocationRepository,
) : ViewModel() {

    val viewStateLiveData = liveData(Dispatchers.IO) {
        val realEstatesLatLng = ArrayList<LatLng>()
        realEstateRepository.getAllRealEstates().collectLatest { realEstateEntityList ->
            realEstateEntityList.forEach { realEstateEntity ->
                realEstatesLatLng.add(realEstateEntity.latLng)
            }
        }

        var userLatLng: LatLng? = null
        locationRepository.getLocationStateFlow().filterNotNull().collectLatest { userLocation ->
            userLatLng = LatLng(
                userLocation.latitude,
                userLocation.longitude
            )
        }

        emit(
            MapViewState(
                realEstatesLatLng,
                userLatLng
            )
        )
    }
}
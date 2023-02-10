package com.openclassrooms.realestatemanager.ui.map

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    locationRepository: LocationRepository
) : ViewModel() {

    val userLatLngLiveData: LiveData<LatLng> = liveData(Dispatchers.IO) {
        locationRepository.getLocationStateFlow()
            .filterNotNull()
            .mapLatest { it ->
                emit(LatLng(it.latitude, it.longitude))
            }
            .collectLatest {
            }
    }

}


package com.openclassrooms.realestatemanager.ui.main

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
): ViewModel() {

    fun startLocationRequest() {
        locationRepository.startLocationRequest()
    }

    fun stopLocationRequest() {
        locationRepository.stopLocationRequest()
    }

}
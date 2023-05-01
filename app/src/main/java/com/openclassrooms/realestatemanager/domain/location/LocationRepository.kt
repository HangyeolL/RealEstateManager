package com.openclassrooms.realestatemanager.domain.location

import android.location.Location
import kotlinx.coroutines.flow.StateFlow

interface LocationRepository {

    fun startLocationRequest()

    fun stopLocationRequest()

    fun getLocationStateFlow(): StateFlow<Location?>
}
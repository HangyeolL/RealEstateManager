package com.openclassrooms.realestatemanager.data.local.repository

import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.*
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationRepository {

    companion object {
        private const val LOCATION_REQUEST_INTERVAL_MS = 10000
        private const val SMALLEST_DISPLACEMENT_THRESHOLD_METER = 25f
    }

    private var callback: LocationCallback? = null

    private val locationMutableStateFlow = MutableStateFlow<Location?>(null)
    private val locationStateFlow: StateFlow<Location?> = locationMutableStateFlow.asStateFlow()

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    override fun startLocationRequest() {
        if (callback == null) {
            callback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location: Location? = locationResult.lastLocation
                    locationMutableStateFlow.value = location
                }
            }
        }
        fusedLocationProviderClient.removeLocationUpdates(callback!!)
        fusedLocationProviderClient.requestLocationUpdates(
            LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                LOCATION_REQUEST_INTERVAL_MS.toLong()
            )
                .setMinUpdateDistanceMeters(SMALLEST_DISPLACEMENT_THRESHOLD_METER)
                .build(),
            callback!!,
            Looper.getMainLooper()
        )
    }

    override fun stopLocationRequest() {
        if (callback != null) {
            fusedLocationProviderClient.removeLocationUpdates(callback!!)
        }
    }

    override fun getLocationStateFlow(): StateFlow<Location?> = locationStateFlow
}
package com.openclassrooms.realestatemanager.domain.geocoding

import com.openclassrooms.realestatemanager.domain.geocoding.model.GeocodingEntity
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface GeocodingRepository {

    suspend fun requestMyGeocodingResponse(userInput: String) : GeocodingEntity?
}
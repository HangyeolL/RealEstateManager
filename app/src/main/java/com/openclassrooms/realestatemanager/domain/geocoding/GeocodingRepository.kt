package com.openclassrooms.realestatemanager.domain.geocoding

import com.openclassrooms.realestatemanager.domain.geocoding.model.GeocodingEntity

interface GeocodingRepository {

    suspend fun requestMyGeocodingResponse(userInput: String) : GeocodingEntity?
}
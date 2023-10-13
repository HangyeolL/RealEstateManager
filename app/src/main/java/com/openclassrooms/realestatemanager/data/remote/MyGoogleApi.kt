package com.openclassrooms.realestatemanager.data.remote

import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.MyAutocompleteResponse
import com.openclassrooms.realestatemanager.data.remote.model.geocoding.MyGeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MyGoogleApi {

    @GET("place/autocomplete/json")
    suspend fun requestAutocompleteResponse(
        @Query("input") input: String,
        @Query("components") components: String,
        @Query("types") types: String,
        @Query("key") key: String,
    ): MyAutocompleteResponse

    @GET("geocode/json")
    suspend fun requestGeocodingResponse(
        @Query("address") address: String,
        @Query("key") key: String,
    ): MyGeocodingResponse
}
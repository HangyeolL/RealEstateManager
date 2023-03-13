package com.openclassrooms.realestatemanager.data.remote

import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.MyAutocompleteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyGoogleApi {

    @GET("place/autocomplete/json")
    fun requestAutocompleteResponse(
        @Query("input") input: String,
        @Query("components") components: String,
        @Query("types") types: String,
        @Query("key") key: String,
    ): Call<MyAutocompleteResponse>

//    @GET("geocode/json")
//    fun requestGeocodingResponse(
//        @Query("address") address: String,
//        @Query("key") key: String,
//    ): Call<>
}
package com.openclassrooms.realestatemanager.data.remote

import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.MyAutocompleteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApi {

    @GET("autocomplete/json")
    fun getAutocompleteData(
        @Query("input") input: String,
        @Query("components") components: String,
        @Query("key") key: String,
    ): Call<MyAutocompleteResponse>

}
package com.openclassrooms.realestatemanager.data.remote.model.geocoding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyGeocodingResponse(

    @SerializedName("results")
    @Expose
    val results: List<ResultResponse>,

    @SerializedName("status")
    @Expose
    val status: String,

)

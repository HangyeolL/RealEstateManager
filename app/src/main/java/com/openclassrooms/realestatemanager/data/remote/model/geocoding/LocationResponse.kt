package com.openclassrooms.realestatemanager.data.remote.model.geocoding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationResponse(

    @SerializedName("lat")
    @Expose
    val lat: Double,

    @SerializedName("lng")
    @Expose
    val lng: Double,

)

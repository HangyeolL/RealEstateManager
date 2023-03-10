package com.openclassrooms.realestatemanager.data.remote.model.geocoding

import android.location.Location
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GeometryResponse(

    @SerializedName("location")
    @Expose
    val location: LocationResponse,
    @SerializedName("location_type")
    @Expose
    val locationType: String,

    @SerializedName("viewport")
    @Expose
    val viewport: ViewportResponse,

)


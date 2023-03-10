package com.openclassrooms.realestatemanager.data.remote.model.geocoding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ViewportResponse(

    @SerializedName("northeast")
    @Expose
    val northeast: NortheastResponse,

    @SerializedName("southwest")
    @Expose
    val southwest: SouthwestResponse,
)

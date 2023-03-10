package com.openclassrooms.realestatemanager.data.remote.model.geocoding

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class AddressComponentResponse (

    @SerializedName("long_name")
    @Expose
    val longName: String,

    @SerializedName("short_name")
    @Expose
    var shortName: String,

    @SerializedName("types")
    @Expose
    var types: List<String>

)


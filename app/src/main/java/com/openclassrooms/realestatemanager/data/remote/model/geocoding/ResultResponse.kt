package com.openclassrooms.realestatemanager.data.remote.model.geocoding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResultResponse(

    @SerializedName("address_components")
    @Expose
    val addressComponents: List<AddressComponentResponse>,

    @SerializedName("formatted_address")
    @Expose
    val formattedAddress: String,

    @SerializedName("geometry")
    @Expose
    val geometry: GeometryResponse,

    @SerializedName("place_id")
    @Expose
    val placeId: String,

    @SerializedName("plus_code")
    @Expose
    val plusCode: PlusCodeResponse,

    @SerializedName("types")
    @Expose
    val types: List<String>,

)
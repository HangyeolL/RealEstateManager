package com.openclassrooms.realestatemanager.data.remote.model.geocoding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlusCodeResponse(

    @SerializedName("compound_code")
    @Expose
    val compoundCode: String,

    @SerializedName("global_code")
    @Expose
    val globalCode: String,

)

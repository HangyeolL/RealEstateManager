package com.openclassrooms.realestatemanager.data.remote.model.autocomplete

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TermResponse(

    @SerializedName("offset")
    @Expose
    private val offset: Int,

    @SerializedName("value")
    @Expose
    private val value: String,

)

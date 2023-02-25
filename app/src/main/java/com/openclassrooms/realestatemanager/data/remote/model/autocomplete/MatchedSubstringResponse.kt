package com.openclassrooms.realestatemanager.data.remote.model.autocomplete

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MatchedSubstringResponse(

    @SerializedName("length")
    @Expose
    private val length: Int,
    @SerializedName("offset")
    @Expose
    private val offset: Int

)

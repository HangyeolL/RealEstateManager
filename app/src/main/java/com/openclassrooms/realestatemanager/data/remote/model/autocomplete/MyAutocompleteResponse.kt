package com.openclassrooms.realestatemanager.data.remote.model.autocomplete

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyAutocompleteResponse(

    @SerializedName("predictions")
    @Expose
    private val predictions: List<PredictionResponse>,

    @SerializedName("status")
    @Expose
    private val status: String,

    )

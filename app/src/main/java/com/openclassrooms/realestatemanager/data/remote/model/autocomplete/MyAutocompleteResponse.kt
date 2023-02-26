package com.openclassrooms.realestatemanager.data.remote.model.autocomplete

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyAutocompleteResponse(

    @SerializedName("predictions")
    @Expose
    val predictions: List<PredictionResponse>,

    @SerializedName("status")
    @Expose
    val status: String,

    )

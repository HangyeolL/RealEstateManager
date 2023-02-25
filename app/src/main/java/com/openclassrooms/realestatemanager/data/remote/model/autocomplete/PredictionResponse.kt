package com.openclassrooms.realestatemanager.data.remote.model.autocomplete

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PredictionResponse(

    @SerializedName("description")
    @Expose
    private val description: String,

    @SerializedName("matched_substrings")
    @Expose
    private val matchedSubstrings: List<MatchedSubstringResponse>? = null,

    @SerializedName("place_id")
    @Expose
    private val placeId: String,

    @SerializedName("reference")
    @Expose
    private val reference: String,

    @SerializedName("structured_formatting")
    @Expose
    private val structuredFormattingResponse: StructuredFormattingResponse,

    @SerializedName("termResponses")
    @Expose
    private val termResponses: List<TermResponse>? = null,

    @SerializedName("types")
    @Expose
    private val types: List<String>? = null,

    )
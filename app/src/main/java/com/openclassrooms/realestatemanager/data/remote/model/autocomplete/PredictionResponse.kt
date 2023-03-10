package com.openclassrooms.realestatemanager.data.remote.model.autocomplete

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PredictionResponse(

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("matched_substrings")
    @Expose
    val matchedSubstrings: List<MatchedSubstringResponse>? = null,

    @SerializedName("place_id")
    @Expose
    val placeId: String,

    @SerializedName("reference")
    @Expose
    val reference: String,

    @SerializedName("structured_formatting")
    @Expose
    val structuredFormattingResponse: StructuredFormattingResponse,

    @SerializedName("termResponses")
    @Expose
    val termResponses: List<TermResponse>? = null,

    @SerializedName("types")
    @Expose
    val types: List<String>? = null,

) {
    override fun toString(): String {
        return "PredictionResponse(description='$description', matchedSubstrings=$matchedSubstrings, placeId='$placeId', reference='$reference', structuredFormattingResponse=$structuredFormattingResponse, termResponses=$termResponses, types=$types)"
    }
}
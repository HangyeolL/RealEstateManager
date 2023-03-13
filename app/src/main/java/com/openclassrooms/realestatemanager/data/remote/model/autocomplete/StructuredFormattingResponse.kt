package com.openclassrooms.realestatemanager.data.remote.model.autocomplete

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StructuredFormattingResponse(

    @SerializedName("main_text")
    @Expose
    val mainText: String,

    @SerializedName("main_text_matched_substrings")
    @Expose
    val mainTextMatchedSubstringResponses: List<MainTextMatchedSubstringResponse>,

    @SerializedName("secondary_text")
    @Expose
    val secondaryText: String,

    )

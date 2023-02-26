package com.openclassrooms.realestatemanager.domain.autocomplete

import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.MyAutocompleteResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AutocompleteRepository {

    fun requestMyAutocompleteResponse(userInput: String,)

    fun getMyAutocompleteResponse(): StateFlow<MyAutocompleteResponse?>

//    fun setUserInput(userInput: String)
}
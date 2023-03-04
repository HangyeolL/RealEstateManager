package com.openclassrooms.realestatemanager.domain.autocomplete

import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.MyAutocompleteResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AutocompleteRepository {

    fun requestMyAutocompleteResponseOfAddress(userInput: String,)

    fun getMyAutocompleteResponseOfAddress(): StateFlow<MyAutocompleteResponse?>

    fun requestMyAutocompleteResponseOfCity(userInput: String)

    fun getMyAutocompleteResponseOfCity(): StateFlow<MyAutocompleteResponse?>

}
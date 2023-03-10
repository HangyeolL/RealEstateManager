package com.openclassrooms.realestatemanager.domain.autocomplete

import com.openclassrooms.realestatemanager.domain.autocomplete.model.AutocompleteEntity
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AutocompleteRepository {

    fun requestMyAutocompleteResponseOfAddress(userInput: String,)

    fun getAutocompleteEntitiesForAddress(): SharedFlow<List<AutocompleteEntity>>

    fun requestMyAutocompleteResponseOfCity(userInput: String)

    fun getAutocompleteEntitiesForCity(): SharedFlow<List<AutocompleteEntity>>

}
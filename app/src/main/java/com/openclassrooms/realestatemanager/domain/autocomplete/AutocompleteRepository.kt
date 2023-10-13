package com.openclassrooms.realestatemanager.domain.autocomplete

import com.openclassrooms.realestatemanager.domain.autocomplete.model.AutocompleteEntity
import kotlinx.coroutines.flow.SharedFlow

interface AutocompleteRepository {

    suspend fun requestMyAutocompleteResponseOfAddress(userInput: String)

    fun getAutocompleteEntitiesForAddress(): SharedFlow<List<AutocompleteEntity>>

    suspend fun requestMyAutocompleteResponseOfCity(userInput: String)

    fun getAutocompleteEntitiesForCity(): SharedFlow<List<AutocompleteEntity>>

}
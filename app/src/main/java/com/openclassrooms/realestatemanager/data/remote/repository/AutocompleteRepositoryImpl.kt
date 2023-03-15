package com.openclassrooms.realestatemanager.data.remote.repository

import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.data.remote.MyGoogleApi
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.model.AutocompleteEntity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException


class AutocompleteRepositoryImpl @Inject constructor(
    private val googleApi: MyGoogleApi,
) : AutocompleteRepository {

    private val autocompleteOfAddressMutableSharedFlow = MutableSharedFlow<List<AutocompleteEntity>>(replay = 1)
    private val autocompleteOfAddressSharedFlow = autocompleteOfAddressMutableSharedFlow.asSharedFlow()

    private val autocompleteOfCityMutableSharedFlow = MutableSharedFlow<List<AutocompleteEntity>>(replay = 1)
    private val autocompleteOfCitySharedFlow = autocompleteOfCityMutableSharedFlow.asSharedFlow()

    override suspend fun requestMyAutocompleteResponseOfAddress(userInput: String) {
        val response = try {
            googleApi.requestAutocompleteResponse(
                userInput,
                "country:fr",
                "address",
                BuildConfig.GOOGLE_API_KEY
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        autocompleteOfAddressMutableSharedFlow.tryEmit(
            response?.predictions?.map { predictionResponse ->
                AutocompleteEntity(
                    placeId = predictionResponse.placeId,
                    text = predictionResponse.description,
                )
            } ?: emptyList()
        )
    }

    override fun getAutocompleteEntitiesForAddress(): SharedFlow<List<AutocompleteEntity>> =
        autocompleteOfAddressSharedFlow

    override suspend fun requestMyAutocompleteResponseOfCity(userInput: String) {
        val response = try {
            googleApi.requestAutocompleteResponse(
                userInput,
                "country:fr",
                "(cities)",
                BuildConfig.GOOGLE_API_KEY
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        autocompleteOfCityMutableSharedFlow.tryEmit(
            response?.predictions?.map { predictionResponse ->
                AutocompleteEntity(
                    placeId = predictionResponse.placeId,
                    text = predictionResponse.structuredFormattingResponse.mainText,
                )
            } ?: emptyList()
        )
    }

    override fun getAutocompleteEntitiesForCity(): SharedFlow<List<AutocompleteEntity>> =
        autocompleteOfCitySharedFlow
}

package com.openclassrooms.realestatemanager.data.remote.repository

import android.util.Log
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.data.remote.GoogleApi
import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.MyAutocompleteResponse
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.model.AutocompleteEntity
import kotlinx.coroutines.flow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class AutocompleteRepositoryImpl @Inject constructor(
    private val googleApi: GoogleApi,
) : AutocompleteRepository {

    private val autocompleteOfAddressMutableSharedFlow = MutableSharedFlow<List<AutocompleteEntity>>(replay = 1)
    private val autocompleteOfAddressSharedFlow = autocompleteOfAddressMutableSharedFlow.asSharedFlow()

    private val autocompleteOfCityMutableSharedFlow = MutableSharedFlow<List<AutocompleteEntity>>(replay = 1)
    private val autocompleteOfCitySharedFlow = autocompleteOfAddressMutableSharedFlow.asSharedFlow()

    override fun requestMyAutocompleteResponseOfAddress(userInput: String) {
        val call = googleApi.requestAutocompleteResponse(
            userInput,
            "country:fr",
            "address",
            BuildConfig.GOOGLE_API_KEY
        )

        call.enqueue(object : Callback<MyAutocompleteResponse?> {
            override fun onResponse(
                call: Call<MyAutocompleteResponse?>,
                response: Response<MyAutocompleteResponse?>
            ) {
                autocompleteOfAddressMutableSharedFlow.tryEmit(
                    response.body()?.let {
                        it.predictions.map { predictionResponse ->
                            AutocompleteEntity(
                                placeId = predictionResponse.placeId,
                                text = predictionResponse.description,
                            )
                        }
                    } ?: emptyList()
                )
            }

            override fun onFailure(call: Call<MyAutocompleteResponse?>, t: Throwable) {
                Log.w("HG", "Get Autocomplete data failed", t)
                autocompleteOfAddressMutableSharedFlow.tryEmit(emptyList())
            }
        })

    }

    override fun getAutocompleteEntitiesForAddress(): SharedFlow<List<AutocompleteEntity>> = autocompleteOfAddressSharedFlow

    override fun requestMyAutocompleteResponseOfCity(userInput: String) {
        val call = googleApi.requestAutocompleteResponse(
            userInput,
            "country:fr",
            "cities",
            BuildConfig.GOOGLE_API_KEY
        )

        call.enqueue(object : Callback<MyAutocompleteResponse?> {
            override fun onResponse(
                call: Call<MyAutocompleteResponse?>,
                response: Response<MyAutocompleteResponse?>
            ) {
                autocompleteOfCityMutableSharedFlow.tryEmit(
                    response.body()?.let {
                        it.predictions.map { predictionResponse ->
                            AutocompleteEntity(
                                placeId = predictionResponse.placeId,
                                text = predictionResponse.description,
                            )
                        }
                    } ?: emptyList()
                )
            }

            override fun onFailure(call: Call<MyAutocompleteResponse?>, t: Throwable) {
                Log.w("HG", "Get Autocomplete data failed", t)
                autocompleteOfCityMutableSharedFlow.tryEmit(emptyList())
            }
        })
    }

    override fun getAutocompleteEntitiesForCity(): SharedFlow<List<AutocompleteEntity>> = autocompleteOfCitySharedFlow
}

package com.openclassrooms.realestatemanager.data.remote.repository

import android.util.Log
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.data.remote.GoogleApi
import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.MyAutocompleteResponse
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class AutocompleteRepositoryImpl @Inject constructor(
    private val googleApi: GoogleApi,
) : AutocompleteRepository {

    private val autocompleteOfAddressMutableStateFlow = MutableStateFlow<MyAutocompleteResponse?>(null)
    private val autocompleteOfAddressStateFlow = autocompleteOfAddressMutableStateFlow.asStateFlow()

    private val autocompleteOfCityMutableStateFlow = MutableStateFlow<MyAutocompleteResponse?>(null)
    private val autocompleteOfCityStateFlow = autocompleteOfAddressMutableStateFlow.asStateFlow()

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
                autocompleteOfAddressMutableStateFlow.value = response.body()
            }

            override fun onFailure(call: Call<MyAutocompleteResponse?>, t: Throwable) {
                Log.w("HG", "Get Autocomplete data failed", t)
                autocompleteOfAddressMutableStateFlow.value = null
            }
        })

    }

    override fun getMyAutocompleteResponseOfAddress(): StateFlow<MyAutocompleteResponse?> = autocompleteOfAddressStateFlow

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
                autocompleteOfCityMutableStateFlow.value = response.body()
            }

            override fun onFailure(call: Call<MyAutocompleteResponse?>, t: Throwable) {
                Log.w("HG", "Get Autocomplete data failed", t)
                autocompleteOfCityMutableStateFlow.value = null
            }
        })
    }

    override fun getMyAutocompleteResponseOfCity(): StateFlow<MyAutocompleteResponse?> = autocompleteOfCityStateFlow
}

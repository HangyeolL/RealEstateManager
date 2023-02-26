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

    private val autocompleteMutableStateFlow = MutableStateFlow<MyAutocompleteResponse?>(null)
    private val autocompleteStateFlow = autocompleteMutableStateFlow.asStateFlow()

//    private val userInputMutableStateFlow = MutableStateFlow<String?>(null)

    override fun requestMyAutocompleteResponse(userInput: String) {
        val call = googleApi.getAutocompleteData(
            userInput,
            "country:fr",
            BuildConfig.GOOGLE_API_KEY
        )

        call.enqueue(object : Callback<MyAutocompleteResponse?> {
            override fun onResponse(
                call: Call<MyAutocompleteResponse?>,
                response: Response<MyAutocompleteResponse?>
            ) {
                autocompleteMutableStateFlow.value = response.body()
            }

            override fun onFailure(call: Call<MyAutocompleteResponse?>, t: Throwable) {
                Log.w("HG", "Get Autocomplete data failed", t)
                autocompleteMutableStateFlow.value = null
            }
        })

    }

    override fun getMyAutocompleteResponse() : StateFlow<MyAutocompleteResponse?> = autocompleteStateFlow

//    override fun setUserInput(userInput: String) {
//        userInputMutableStateFlow.value = userInput
//    }
}
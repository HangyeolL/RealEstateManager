package com.openclassrooms.realestatemanager.data.remote.repository

import android.util.Log
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.data.remote.GoogleApi
import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.MyAutocompleteResponse
import com.openclassrooms.realestatemanager.domain.autocomplete.AutoCompleteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class AutoCompleteRepositoryImpl @Inject constructor(
    private val googleApi: GoogleApi,
) : AutoCompleteRepository {

    private val autoCompleteMutableStateFlow = MutableStateFlow<MyAutocompleteResponse?>(null)
    private val autoCompleteStateFlow = autoCompleteMutableStateFlow.asStateFlow()

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
                autoCompleteMutableStateFlow.value = response.body()
            }

            override fun onFailure(call: Call<MyAutocompleteResponse?>, t: Throwable) {
                Log.w("HG", "Get Autocomplete data failed", t)
                autoCompleteMutableStateFlow.value = null
            }
        })

    }

    override fun getMyAutocompleteResponse() : StateFlow<MyAutocompleteResponse?> = autoCompleteStateFlow

//    override fun setUserInput(userInput: String) {
//        userInputMutableStateFlow.value = userInput
//    }
}
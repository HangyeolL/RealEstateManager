package com.openclassrooms.realestatemanager.data.remote.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.data.remote.MyGoogleApi
import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.MyAutocompleteResponse
import com.openclassrooms.realestatemanager.data.remote.model.geocoding.MyGeocodingResponse
import com.openclassrooms.realestatemanager.domain.autocomplete.model.AutocompleteEntity
import com.openclassrooms.realestatemanager.domain.geocoding.GeocodingRepository
import com.openclassrooms.realestatemanager.domain.geocoding.model.GeocodingEntity
import kotlinx.coroutines.flow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GeocodingRepositoryImpl @Inject constructor(
    private val googleApi: MyGoogleApi
) : GeocodingRepository {

    private val geocodingMutableStateFlow = MutableStateFlow<GeocodingEntity?>(null)
    private val geocodingStateFlow = geocodingMutableStateFlow.asStateFlow()

    override fun requestMyGeocodingResponse(userInput: String) {
        val call = googleApi.requestGeocodingResponse(
            userInput,
            BuildConfig.GOOGLE_API_KEY
        )

        call.enqueue(object : Callback<MyGeocodingResponse?> {
            override fun onResponse(
                call: Call<MyGeocodingResponse?>,
                response: Response<MyGeocodingResponse?>
            ) {
                var geocodingEntity: GeocodingEntity? = null

                    response.body()?.results?.forEach { resultResponse ->
                    if (userInput.equals(resultResponse.formattedAddress)) {
                        geocodingEntity = GeocodingEntity(
                            LatLng(
                                resultResponse.geometry.location.lat,
                                resultResponse.geometry.location.lng
                            )
                        )
                    }
                }

                geocodingMutableStateFlow.value = geocodingEntity

            }

            override fun onFailure(call: Call<MyGeocodingResponse?>, t: Throwable) {
                Log.w("HG", "Get Autocomplete data failed", t)
                geocodingMutableStateFlow.value = null
            }
        })

    }

    override fun getGeocodingEntities(): StateFlow<GeocodingEntity?> = geocodingStateFlow


}
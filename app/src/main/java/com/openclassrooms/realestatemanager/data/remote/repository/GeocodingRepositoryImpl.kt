package com.openclassrooms.realestatemanager.data.remote.repository

import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.data.remote.MyGoogleApi
import com.openclassrooms.realestatemanager.domain.geocoding.GeocodingRepository
import com.openclassrooms.realestatemanager.domain.geocoding.model.GeocodingEntity
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GeocodingRepositoryImpl @Inject constructor(
    private val googleApi: MyGoogleApi
) : GeocodingRepository {

    override suspend fun requestMyGeocodingResponse(userInput: String): GeocodingEntity? {
        val response = try {
            googleApi.requestGeocodingResponse(
                userInput,
                BuildConfig.GOOGLE_API_KEY
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return response?.results?.find { resultResponse ->
            userInput == resultResponse.formattedAddress
        }?.let { resultResponse ->
            GeocodingEntity(
                LatLng(
                    resultResponse.geometry.location.lat,
                    resultResponse.geometry.location.lng
                )
            )
        }
    }
}
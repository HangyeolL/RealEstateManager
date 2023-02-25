package com.openclassrooms.realestatemanager.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoogleApiHolder {

    val googlePlaceSearchUrl = "https://maps.googleapis.com/maps/api/place/"

    fun getInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(googlePlaceSearchUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .client(
                OkHttpClient()
                    .newBuilder()
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
            .build()

}
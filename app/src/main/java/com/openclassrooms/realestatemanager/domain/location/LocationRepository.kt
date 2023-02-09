package com.openclassrooms.realestatemanager.domain.location

interface LocationRepository {

    fun startLocationRequest()
    fun stopLocationRequest()
}
package com.openclassrooms.realestatemanager.domain.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DataStoreRepository {

    suspend fun writeDollarBooleanToTrue()

    suspend fun writeDollarBooleanToFalse()

    fun readDollarBoolean(): Flow<Boolean>

//    suspend fun writeEuroBoolean(value: Boolean)

//    fun readEuroBoolean(): Flow<Boolean>

}
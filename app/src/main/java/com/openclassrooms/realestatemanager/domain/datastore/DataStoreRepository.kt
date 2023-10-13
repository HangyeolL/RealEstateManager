package com.openclassrooms.realestatemanager.domain.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun writeDollarBooleanToTrue()

    suspend fun writeDollarBooleanToFalse()

    fun readDollarBoolean(): Flow<Boolean>

}
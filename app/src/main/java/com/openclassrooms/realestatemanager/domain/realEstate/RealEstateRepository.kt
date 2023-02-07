package com.openclassrooms.realestatemanager.domain.realEstate

import androidx.room.Query
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RealEstateRepository {

    suspend fun upsertRealEstate(realEstate: RealEstateEntity)

    fun getAllRealEstates(): Flow<List<RealEstateEntity>>

    fun getRealEstateById(id: Int): Flow<RealEstateEntity>

    suspend fun deleteRealEstate(realEstateId: Int): Int

}


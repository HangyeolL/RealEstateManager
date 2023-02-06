package com.openclassrooms.realestatemanager.domain.realEstate

import androidx.room.Query
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
import kotlinx.coroutines.flow.Flow

interface RealEstateRepository {

    suspend fun upsertRealEstate(realEstate: RealEstateEntity)

    fun getAllRealEstates(): Flow<List<RealEstateEntity>>


    suspend fun deleteRealEstate(realEstateId: Int): Int

}


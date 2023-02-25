package com.openclassrooms.realestatemanager.domain.realEstate

import androidx.room.*
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstatePhoto
import com.openclassrooms.realestatemanager.data.local.model.RealEstateWithPhotos
import kotlinx.coroutines.flow.Flow

interface RealEstateRepository {

    suspend fun upsertRealEstate(realEstateEntity: RealEstateEntity)

    suspend fun insertRealEstatePhoto(realEstatePhoto: RealEstatePhoto)

    suspend fun deleteRealEstate(realEstateId: Int)

    fun deleteRealEstatePhoto(realEstatePhoto: RealEstatePhoto)

    fun getRealEstateById(realEstateId: Int) : Flow<RealEstateWithPhotos>

    fun getRealEstatesWithPhotos(): Flow<List<RealEstateWithPhotos>>

}


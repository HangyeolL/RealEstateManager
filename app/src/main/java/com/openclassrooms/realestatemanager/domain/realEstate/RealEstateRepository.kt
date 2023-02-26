package com.openclassrooms.realestatemanager.domain.realEstate

import androidx.room.*
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstatePhotoEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstateWithPhotos
import kotlinx.coroutines.flow.Flow

interface RealEstateRepository {

    suspend fun upsertRealEstate(realEstateEntity: RealEstateEntity)

    suspend fun insertRealEstatePhoto(realEstatePhotoEntity: RealEstatePhotoEntity)

    suspend fun deleteRealEstate(realEstateId: Int)

    fun deleteRealEstatePhoto(realEstatePhotoEntity: RealEstatePhotoEntity)

    fun getRealEstateById(realEstateId: Int) : Flow<RealEstateWithPhotos>

    fun getRealEstatesWithPhotos(): Flow<List<RealEstateWithPhotos>>

}


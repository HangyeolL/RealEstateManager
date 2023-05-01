package com.openclassrooms.realestatemanager.domain.realEstate

import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstatePhotoEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstateWithPhotos
import kotlinx.coroutines.flow.Flow

interface RealEstateRepository {

    suspend fun insertRealEstate(realEstateEntity: RealEstateEntity, onRealEstateInserted: (Long) -> Unit): Long

    suspend fun updateRealEstate(realEstateEntity: RealEstateEntity)

    suspend fun deleteRealEstate(realEstateId: Int)

    suspend fun getAllRealEstates() : Flow<List<RealEstateEntity>>

    fun getRealEstateWithPhotosById(realEstateId: Int) : Flow<RealEstateWithPhotos>

    suspend fun insertRealEstatePhoto(realEstatePhotoEntity: RealEstatePhotoEntity)

    fun deleteRealEstatePhoto(realEstatePhotoEntity: RealEstatePhotoEntity)

    fun getRealEstatesWithPhotos(): Flow<List<RealEstateWithPhotos>>

}


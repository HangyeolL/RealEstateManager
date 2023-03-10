package com.openclassrooms.realestatemanager.data.local.repository

import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstatePhotoEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstateWithPhotos
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealEstateRepositoryImpl @Inject constructor(
    private val realEstateDao: RealEstateDao
) : RealEstateRepository {
    override suspend fun upsertRealEstate(realEstateEntity: RealEstateEntity) = realEstateDao.upsertRealEstate(realEstateEntity)

    override suspend fun insertRealEstatePhoto(realEstatePhotoEntity: RealEstatePhotoEntity) = realEstateDao.insertRealEstatePhoto(realEstatePhotoEntity)

    override suspend fun deleteRealEstate(realEstateId: Int) = realEstateDao.deleteRealEstate(realEstateId)

    override fun deleteRealEstatePhoto(realEstatePhotoEntity: RealEstatePhotoEntity) = realEstateDao.deleteRealEstatePhoto(realEstatePhotoEntity)

    override fun getRealEstateById(realEstateId: Int): Flow<RealEstateWithPhotos> = realEstateDao.getRealEstateById(realEstateId)

    override fun getRealEstatesWithPhotos(): Flow<List<RealEstateWithPhotos>> = realEstateDao.getRealEstatesWithPhotos()
}
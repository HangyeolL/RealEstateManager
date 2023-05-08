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

    override suspend fun insertRealEstate(
        realEstateEntity: RealEstateEntity,
        onRealEstateInserted: (Long) -> Unit
    ): Long {
        val insertedRealEstateId = realEstateDao.insertRealEstate(realEstateEntity)
        onRealEstateInserted(insertedRealEstateId)
        return insertedRealEstateId
    }

    override suspend fun updateRealEstate(realEstateEntity: RealEstateEntity) =
        realEstateDao.updateRealEstate(realEstateEntity)

    override suspend fun deleteRealEstate(realEstateId: Int) =
        realEstateDao.deleteRealEstate(realEstateId)

    override fun getAllRealEstates(): Flow<List<RealEstateEntity>> =
        realEstateDao.getAllRealEstates()

    override suspend fun insertRealEstatePhoto(
        realEstatePhotoEntity: RealEstatePhotoEntity
    ) = realEstateDao.insertRealEstatePhoto(realEstatePhotoEntity)

    override fun deleteRealEstatePhoto(realEstatePhotoEntity: RealEstatePhotoEntity) =
        realEstateDao.deleteRealEstatePhoto(realEstatePhotoEntity)

    override fun getRealEstateWithPhotosById(realEstateId: Int): Flow<RealEstateWithPhotos> =
        realEstateDao.getRealEstateWithPhotosById(realEstateId)

    override fun getRealEstatesWithPhotos(): Flow<List<RealEstateWithPhotos>> =
        realEstateDao.getRealEstatesWithPhotos()

}
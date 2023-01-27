package com.openclassrooms.realestatemanager.data.local.repository

import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealEstateRepositoryImpl @Inject constructor(private val realEstateDao: RealEstateDao) : RealEstateRepository {
    override suspend fun upsertRealEstate(realEstate: RealEstateEntity) {
        realEstateDao.upsertRealEstate(realEstate)
    }

    override fun getAllRealEstates(): Flow<List<RealEstateEntity>> =
        realEstateDao.getAllRealEstates()

    override suspend fun deleteRealEstate(realEstateId: Int): Int =
        realEstateDao.deleteRealEstate(realEstateId)

}
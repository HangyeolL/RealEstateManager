package com.openclassrooms.realestatemanager.data.local.repository

import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CurrentRealEstateRepositoryImpl @Inject constructor(
    private val realEstateDao: RealEstateDao
) : CurrentRealEstateRepository {

    private val _currentRealEstateIdState = MutableStateFlow<Int?>(null)
    val currentRealEstateIdState = _currentRealEstateIdState.asStateFlow()


    override fun getRealEstateById(id: Int): Flow<RealEstateEntity> = realEstateDao.getRealEstateById(id)

    override fun setCurrentRealEstateId(currentId: Int) {
        _currentRealEstateIdState.value = currentId
    }

}
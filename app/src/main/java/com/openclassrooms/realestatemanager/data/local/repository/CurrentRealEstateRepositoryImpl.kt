package com.openclassrooms.realestatemanager.data.local.repository

import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CurrentRealEstateRepositoryImpl @Inject constructor(
): CurrentRealEstateRepository {

    private val _currentRealEstateIdState = MutableStateFlow<Int?>(null)
    private val currentRealEstateIdState = _currentRealEstateIdState.asStateFlow()

    override fun getCurrentRealEstateId(): StateFlow<Int?> = currentRealEstateIdState

    override fun setCurrentRealEstateId(currentId: Int) {
        _currentRealEstateIdState.value = currentId
    }

}
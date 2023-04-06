package com.openclassrooms.realestatemanager.data.local.repository

import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CurrentRealEstateRepositoryImpl @Inject constructor(

) : CurrentRealEstateRepository {

    private val currentRealEstateIdMutableStateFlow: MutableStateFlow<Int?> = MutableStateFlow<Int?>(null)
    val currentRealEstateIdStateFlow: StateFlow<Int?> = currentRealEstateIdMutableStateFlow.asStateFlow()

    override fun getCurrentRealEstateId(): StateFlow<Int?> = currentRealEstateIdStateFlow

    override fun setCurrentRealEstateId(currentId: Int) {
        currentRealEstateIdMutableStateFlow.value = currentId
    }

}
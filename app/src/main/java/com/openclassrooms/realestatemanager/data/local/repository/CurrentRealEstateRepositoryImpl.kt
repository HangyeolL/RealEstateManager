package com.openclassrooms.realestatemanager.data.local.repository

import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CurrentRealEstateRepositoryImpl @Inject constructor(

) : CurrentRealEstateRepository {

    private val currentMailIdMutableStateFlow: MutableStateFlow<Int?> = MutableStateFlow<Int?>(null)
    val currentMailIdStateFlow: StateFlow<Int?> = currentMailIdMutableStateFlow.asStateFlow()

    override fun getCurrentRealEstateId(): StateFlow<Int?> = currentMailIdStateFlow

    override fun setCurrentRealEstateId(currentId: Int) {
        currentMailIdMutableStateFlow.value = currentId
    }

}
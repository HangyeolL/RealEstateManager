package com.openclassrooms.realestatemanager.data.local.repository

import android.util.Log
import com.openclassrooms.realestatemanager.domain.realestate.CurrentRealEstateIdRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CurrentRealEstateIdRepositoryImpl @Inject constructor(

) : CurrentRealEstateIdRepository {

    private val currentRealEstateIdMutableStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)
    private val currentRealEstateIdStateFlow: StateFlow<Int> = currentRealEstateIdMutableStateFlow.asStateFlow()

    override fun getCurrentRealEstateId(): StateFlow<Int> = currentRealEstateIdStateFlow

    override fun setCurrentRealEstateId(currentId: Int) {
        currentRealEstateIdMutableStateFlow.value = currentId
        Log.d("HG", "CurrentRealEstateIdRepository setting realEstateId to ${currentRealEstateIdStateFlow.value}")
    }

}
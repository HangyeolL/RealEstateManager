package com.openclassrooms.realestatemanager.domain.realEstate

import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CurrentRealEstateRepository {

    fun getCurrentRealEstateIdStateFlow() : StateFlow<Int?>

    fun setCurrentRealEstateId(currentId: Int)

}
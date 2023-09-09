package com.openclassrooms.realestatemanager.domain.realestate

import kotlinx.coroutines.flow.StateFlow

interface CurrentRealEstateIdRepository {

    fun getCurrentRealEstateId(): StateFlow<Int>

    fun setCurrentRealEstateId(currentId: Int)

}
package com.openclassrooms.realestatemanager.domain.realestate

import kotlinx.coroutines.flow.StateFlow

interface CurrentRealEstateRepository {

    fun getCurrentRealEstateId(): StateFlow<Int>

    fun setCurrentRealEstateId(currentId: Int)

}
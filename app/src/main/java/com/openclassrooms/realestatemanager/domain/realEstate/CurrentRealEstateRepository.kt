package com.openclassrooms.realestatemanager.domain.realEstate

import kotlinx.coroutines.flow.StateFlow

interface CurrentRealEstateRepository {

    fun getCurrentRealEstateIdStateFlow() : StateFlow<Int?>

    fun setCurrentRealEstateId(currentId: Int)

}
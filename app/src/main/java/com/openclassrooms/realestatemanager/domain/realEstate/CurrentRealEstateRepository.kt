package com.openclassrooms.realestatemanager.domain.realEstate

import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
import kotlinx.coroutines.flow.Flow

interface CurrentRealEstateRepository {

    fun getRealEstateById(id: Int) : Flow<RealEstateEntity>

    fun setCurrentRealEstateId(currentId: Int)

}
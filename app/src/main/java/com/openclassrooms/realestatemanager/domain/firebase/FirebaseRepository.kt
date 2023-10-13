package com.openclassrooms.realestatemanager.domain.firebase

import com.openclassrooms.realestatemanager.data.local.model.RealEstateWithPhotos

interface FirebaseRepository {

    suspend fun setRealEstateWithPhotosList(realEstateWithPhotosList: List<RealEstateWithPhotos>)

}
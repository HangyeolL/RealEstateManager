package com.openclassrooms.realestatemanager.data.remote.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.openclassrooms.realestatemanager.data.local.model.RealEstateWithPhotos
import com.openclassrooms.realestatemanager.domain.firebase.FirebaseRepository
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseFirestore : FirebaseFirestore
) : FirebaseRepository {

    override suspend fun setRealEstateWithPhotosList(realEstateWithPhotosList: List<RealEstateWithPhotos>) {
        realEstateWithPhotosList.forEach { realEstateWithPhotos ->
            firebaseFirestore
                .collection("realEstateWithPhotosList")
                .document(realEstateWithPhotos.realEstateEntity.realEstateId.toString())
                .set(realEstateWithPhotos)
        }

    }


}
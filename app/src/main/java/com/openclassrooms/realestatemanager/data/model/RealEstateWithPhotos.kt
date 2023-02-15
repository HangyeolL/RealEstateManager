package com.openclassrooms.realestatemanager.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class RealEstateWithPhotos(
    @Embedded val realEstateEntity: RealEstateEntity,
    @Relation(
        parentColumn =
    )
    val photoList : List<Photo>
)

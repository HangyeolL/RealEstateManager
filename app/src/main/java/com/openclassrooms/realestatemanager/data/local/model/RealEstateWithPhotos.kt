package com.openclassrooms.realestatemanager.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class RealEstateWithPhotos(
    @Embedded val realEstateEntity: RealEstateEntity,
    @Relation(
        parentColumn = "realEstateId",
        entityColumn = "realEstateIdOfPhoto",
        entity = RealEstatePhotoEntity::class
    )
    val realEstatePhotoLists : List<RealEstatePhotoEntity>
)

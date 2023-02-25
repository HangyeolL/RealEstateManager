package com.openclassrooms.realestatemanager.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "realEstatePhotos",
    foreignKeys = [
        ForeignKey(
            entity = RealEstateEntity::class,
            parentColumns = ["realEstateId"],
            childColumns = ["realEstateIdOfPhoto"],
        )
    ]
)
data class RealEstatePhotoEntity(

    @PrimaryKey(autoGenerate = true)
    val photoId: Int = 0,

    @ColumnInfo(index = true)
    val realEstateIdOfPhoto: Int,

    val url : String,

    val description: String?
)

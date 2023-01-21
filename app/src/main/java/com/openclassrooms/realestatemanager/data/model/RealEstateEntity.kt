package com.openclassrooms.realestatemanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "realEstates"
)
data class RealEstateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val creationTime: Long,
    val imageList: List<Int>,
    val name: String,
    val type: String,
    val descriptionBody: String,
    val squareMeter: Int,
    val city : String,
    val price: Int,
    val numberOfRooms: Int,
    val numberOfBathrooms: Int,
    val numberOfBedrooms: Int,
    val address: String
)

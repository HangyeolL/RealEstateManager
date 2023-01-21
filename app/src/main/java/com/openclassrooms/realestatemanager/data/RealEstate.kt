package com.openclassrooms.realestatemanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "realEstates"
)
data class RealEstateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val creationTime: Long,
    val name: String,
    val type: String,
    val price: Int,
    val surface: Int,
    val pieces: Int,
    val roomNumber: Int,
    val address: String
)

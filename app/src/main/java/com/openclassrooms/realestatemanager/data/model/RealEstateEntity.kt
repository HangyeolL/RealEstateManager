package com.openclassrooms.realestatemanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "realEstates")
data class RealEstateEntity(

    @PrimaryKey(autoGenerate = true)
    val realEstateId: Int = 0,
    val type: String,
    val descriptionBody: String,
    val squareMeter: Int,
    val city : String,
    val price: Int,
    val numberOfRooms: Int,
    val numberOfBathrooms: Int,
    val numberOfBedrooms: Int,
    val address: String,
    val garage: Boolean,
    val guard: Boolean,
    val garden: Boolean,
    val elevator: Boolean,
    val groceryStoreNearby: Boolean,
    val isSoldOut: Boolean,
    val dateOfSold: String?,
    val marketSince: String,
    val agentIdInCharge: Int,
    val latLng: LatLng
)

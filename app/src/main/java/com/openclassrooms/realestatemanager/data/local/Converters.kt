package com.openclassrooms.realestatemanager.data.local

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng

class Converters {

    @TypeConverter
    fun fromLatLngToString(latLng: LatLng): String {
        return "${latLng.latitude},${latLng.longitude}"
    }

    @TypeConverter
    fun fromStringToLatLng(string: String): LatLng {
        val splitString = string.split(",")
        return LatLng(splitString.first().toDouble(), splitString.last().toDouble())
    }

}
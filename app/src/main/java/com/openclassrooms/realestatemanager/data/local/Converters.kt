package com.openclassrooms.realestatemanager.data.local

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromStringToListString(value: String?): List<String>? {
        val listType = object :
            TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListStringToString(list: List<String?>?): String? {
        return Gson().toJson(list)
    }

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
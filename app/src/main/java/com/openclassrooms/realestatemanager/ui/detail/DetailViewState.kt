package com.openclassrooms.realestatemanager.ui.detail

import android.provider.ContactsContract
import com.google.android.gms.maps.model.LatLng

data class DetailViewState(
//    val imageList: List<Int>,
    val descriptionBody: String,
    val squareMeter: Int,
    val numberOfRooms: Int,
    val numberOfBathrooms: Int,
    val numberOfBedrooms: Int,
    val address: String,
    val latLng: LatLng?,
    val agentName: String,
//    val agentPhoto: String

    val isViewVisible: Boolean = false

)

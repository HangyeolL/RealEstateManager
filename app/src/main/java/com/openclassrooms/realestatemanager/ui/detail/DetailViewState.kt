package com.openclassrooms.realestatemanager.ui.detail

import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoItemViewState

data class DetailViewState(

    val realEstatePhotoItemViewStateList: List<RealEstatePhotoItemViewState.Content>,

    val descriptionBody: String,
    val squareMeter: Int,
    val numberOfRooms: Int,
    val numberOfBathrooms: Int,
    val numberOfBedrooms: Int,
    val address: String,
    val agentName: String,
    val agentPhotoUrl: String,

    val isViewVisible: Boolean = false

)

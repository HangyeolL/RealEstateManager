package com.openclassrooms.realestatemanager.ui.detail

import com.openclassrooms.realestatemanager.design_system.photo_carousel.RealEstatePhotoItemViewState

data class DetailViewState(

    val itemViewStateList: List<RealEstatePhotoItemViewState>,

    val descriptionBody: String,
    val squareMeter: Int,
    val numberOfRooms: Int,
    val numberOfBathrooms: Int,
    val numberOfBedrooms: Int,
    val address: String,
    val agentName: String,

    val isViewVisible: Boolean = false

)

package com.openclassrooms.realestatemanager.ui.detail

data class DetailViewState(
    val imageList: List<Int>,
    val descriptionBody: String,
    val squareMeter: Int,
    val numberOfRooms: Int,
    val numberOfBathrooms: Int,
    val numberOfBedrooms: Int,
    val address: String,
)

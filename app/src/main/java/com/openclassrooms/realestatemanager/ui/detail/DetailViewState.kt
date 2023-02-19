package com.openclassrooms.realestatemanager.ui.detail

data class DetailViewState(

    val itemViewStateList: List<DetailListItemViewState>,
    val descriptionBody: String,
    val squareMeter: Int,
    val numberOfRooms: Int,
    val numberOfBathrooms: Int,
    val numberOfBedrooms: Int,
    val address: String,
    val agentName: String,
    val isViewVisible: Boolean = false

)

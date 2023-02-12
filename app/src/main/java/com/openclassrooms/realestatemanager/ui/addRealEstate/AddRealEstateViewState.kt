package com.openclassrooms.realestatemanager.ui.addRealEstate

data class AddRealEstateViewState(

    val typeSpinnerItemViewState: AddRealEstateTypeSpinnerItemViewState,
    val address: String,
    val numberOfRooms: Int,
    val numberOfBathrooms: Int,
    val numberOfBedrooms: Int,
    val squareMeter: Int,
    val marketSince: String,
    val price: Int,
    val garage: Boolean,
    val guard: Boolean,
    val garden: Boolean,
    val elevator: Boolean,
    val groceryStoreNearby: Boolean,
    val isSoldOut: Boolean,
    val dataOfSold: String?,
    val description: String,
    val agentSpinnerItemViewState: AddRealEstateAgentSpinnerItemViewState,
    val imageList: List<String>,

    )


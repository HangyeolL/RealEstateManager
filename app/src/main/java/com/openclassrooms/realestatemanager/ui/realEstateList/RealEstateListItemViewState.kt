package com.openclassrooms.realestatemanager.ui.realEstateList

data class RealEstateListItemViewState(
    val id: Int,
    val image: Int,
    val type: String,
    val city: String,
    val price: Int
)
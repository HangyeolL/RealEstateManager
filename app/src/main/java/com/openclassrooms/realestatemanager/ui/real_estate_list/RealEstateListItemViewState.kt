package com.openclassrooms.realestatemanager.ui.real_estate_list

data class RealEstateListItemViewState(
    val id: Int,
    val imageUrl: String,
    val type: String,
    val city: String,
    val price: Int,
)
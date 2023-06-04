package com.openclassrooms.realestatemanager.ui.real_estate_list

import androidx.annotation.DrawableRes

data class RealEstateListItemViewState(
    val id: Int,
    val imageUrl: String,
    val type: String,
    val city: String,
    val price: Int,
    val isSoldOut: Boolean,
)
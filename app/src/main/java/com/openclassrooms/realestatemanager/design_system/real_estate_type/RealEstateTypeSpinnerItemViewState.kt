package com.openclassrooms.realestatemanager.design_system.real_estate_type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class RealEstateTypeSpinnerItemViewState(

    @DrawableRes
    val icon: Int,

    val type: String,

)

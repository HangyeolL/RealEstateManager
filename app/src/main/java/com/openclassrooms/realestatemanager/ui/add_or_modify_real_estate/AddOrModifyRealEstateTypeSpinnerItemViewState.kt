package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import androidx.annotation.DrawableRes

data class AddOrModifyRealEstateTypeSpinnerItemViewState(
    @DrawableRes val icon: Int,
    val type: String, // TODO Hangyeol Stringres!
)

package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class AddOrModifyRealEstateTypeSpinnerItemViewState(
    @DrawableRes val icon: Int,
    val type: String,
) {
    override fun toString(): String {
        return "AddOrModifyRealEstateTypeSpinnerItemViewState(icon=$icon, type='$type')"
    }
}

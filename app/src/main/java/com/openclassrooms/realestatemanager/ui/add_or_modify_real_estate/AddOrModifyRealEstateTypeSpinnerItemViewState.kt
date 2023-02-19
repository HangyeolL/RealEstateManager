package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.graphics.drawable.Drawable

data class AddOrModifyRealEstateTypeSpinnerItemViewState(
    //TODO What is the best way of setting this in ViewModel ?
//           I dont like that the fact this can be null
    val icon: Drawable?,
    val type: String,
)

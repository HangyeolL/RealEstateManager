package com.openclassrooms.realestatemanager.ui.map_view

import com.google.android.gms.maps.model.LatLng

data class MapMarkerViewState(
    val realEstateAddress: String,
    val realEstateLatLng: LatLng,
    val selectedRealEstateId: Int,
)
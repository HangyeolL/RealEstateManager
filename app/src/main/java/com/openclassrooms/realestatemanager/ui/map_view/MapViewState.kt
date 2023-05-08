package com.openclassrooms.realestatemanager.ui.map_view

import com.google.android.gms.maps.model.LatLng

data class MapViewState(
     val mapMarkerViewStateList: List<MapMarkerViewState>,
     val userLocationLatLng: LatLng,
)
package com.openclassrooms.realestatemanager.ui.map

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.openclassrooms.realestatemanager.ui.realEstateList.RealEstateListFragment

class MapFragment : SupportMapFragment(), OnMapReadyCallback{

    companion object {
        fun newInstance() = MapFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

    }

}
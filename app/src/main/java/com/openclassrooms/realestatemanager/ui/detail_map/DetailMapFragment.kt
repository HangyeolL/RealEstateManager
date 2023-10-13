package com.openclassrooms.realestatemanager.ui.detail_map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.ui.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMapFragment : SupportMapFragment(), OnMapReadyCallback {

    // Using parent's view model
    private val viewModel by viewModels<DetailViewModel>(ownerProducer = {requireParentFragment()})

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN

        viewModel.mapViewStateLiveData.observe(viewLifecycleOwner) {
            googleMap.clear()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it.latLng, 14f))
            googleMap.addMarker(
                MarkerOptions().position(it.latLng)
            )
        }
    }

}
package com.openclassrooms.realestatemanager.ui.map_view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapViewFragment : SupportMapFragment(), OnMapReadyCallback {

    private val viewModel by viewModels<MapViewModel>()

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_FragmentContainerView_navHost
        )

        getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        googleMap.isMyLocationEnabled = true

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { mapViewState ->

            mapViewState.mapMarkerViewStateList.forEach { mapMarkerViewState ->
                googleMap.addMarker(
                    MarkerOptions()
                        .position(mapMarkerViewState.realEstateLatLng)
                        .title(mapMarkerViewState.realEstateAddress)
                )?.tag = mapMarkerViewState.selectedRealEstateId
            }

        }

        googleMap.setOnInfoWindowClickListener { marker ->
            Log.d("HL", "Tag of the clicked marker to Int : ${marker.tag.toString().toInt()}")
            viewModel.onMarkerInfoWindowClicked(marker.tag.toString().toInt()) {
                navController.navigate(
                    MapViewFragmentDirections.actionToDetailFragment()
                )
            }
        }

        viewModel.userLocationViewStateLiveData.observe(viewLifecycleOwner) { userLocationViewState ->
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    userLocationViewState.userLocation,
                    13f
                )
            )
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.removeItem(R.id.toolbar_menu_modify)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_menu_create -> {
                navController.navigate(
                    MapViewFragmentDirections.actionToAddOrModifyRealEstateFragment()
                )
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
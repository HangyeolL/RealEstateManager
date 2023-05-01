package com.openclassrooms.realestatemanager.ui.map_view

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
import com.openclassrooms.realestatemanager.ui.real_estate_list.RealEstateListFragmentDirections
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

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { mapViewState ->
            googleMap.clear()

            mapViewState.userLocationLatLng?.let { userLatLng ->
                CameraUpdateFactory.newLatLngZoom(
                    userLatLng,
                    14f
                )
            }?.let { cameraUpdate ->
                googleMap.moveCamera(cameraUpdate)
            }

            mapViewState.realEstatesLatLng.forEach { realEstateLatLng ->
                googleMap.addMarker(
                    MarkerOptions().position(realEstateLatLng)
                )
            }
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
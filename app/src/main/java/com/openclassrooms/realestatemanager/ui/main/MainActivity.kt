package com.openclassrooms.realestatemanager.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.MainActivityBinding
import com.openclassrooms.realestatemanager.ui.real_estate_list.RealEstateListFragment
import com.openclassrooms.realestatemanager.ui.real_estate_list.RealEstateListFragmentDirections
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private val binding by viewBinding { MainActivityBinding.inflate(it) }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.mainFragmentContainerViewNavHost.id) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.mainDrawerLayout)

        navController.addOnDestinationChangedListener(this)

        binding.mainToolbar.setupWithNavController(navController, appBarConfiguration)
        binding.mainNavigationView.setupWithNavController(navController)

        binding.mainNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.main_navigationView_mapView -> {
                    Log.d("HL", "Navigate from MainActivity to MapViewFragment")
                    navController.navigate(RealEstateListFragmentDirections.actionRealEstateListFragmentToMapViewFragment())
                }
            }
            true
        }

    }

    // Inflate toolbar's menu in parent activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.real_estate_list_toolbar_menu, menu)
        return true
    }

    // Take care of only create and search case in activity
    // TODO Modify case in landscape mode should be taken care of in Fragment
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_menu_create -> {
                Log.d("HL", "MainActivity handling toolBar menu create")
                navController.navigate(RealEstateListFragmentDirections.actionToAddOrModifyRealEstateFragment())
                return true
            }
            R.id.toolbar_menu_search -> {
                Log.d("HG", "MainActivity handling toolBar menu search")
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.mapViewFragment -> {
                binding.mainToolbar.menu.clear()
            }

            else -> {

            }
        }
    }
}
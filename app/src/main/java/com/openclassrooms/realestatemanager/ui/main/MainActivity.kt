package com.openclassrooms.realestatemanager.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.MainActivityBinding
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    companion object {
        fun navigate(context: Context) = Intent(context, MainActivity::class.java)
    }

    private val binding by viewBinding { MainActivityBinding.inflate(it) }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.mainFragmentContainerViewNavHost.id) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(this)

        setContentView(binding.root)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.detailFragment -> {
                supportActionBar?.setHomeButtonEnabled(!resources.getBoolean(R.bool.isTablet))
                supportActionBar?.setDisplayHomeAsUpEnabled(!resources.getBoolean(R.bool.isTablet))
            }
            else -> {
                supportActionBar?.setHomeButtonEnabled(false)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }



//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean =
//        when (item.itemId) {
//            R.id.main_toolbar_menu_create -> {
//                viewModel.onToolBarMenuCreateClicked()
//                true
//            }
//            R.id.main_toolbar_menu_modify -> {
//                true
//            }
//            // If we got here, the user's action was not recognized.
//            // Invoke the superclass to handle it.
//            else -> super.onOptionsItemSelected(item)
//        }


//    private fun setUpToolBarAndDrawerLayout() {
//        setSupportActionBar(binding.mainToolbar)
//        val actionBarDrawerToggle = ActionBarDrawerToggle(
//            this,
//            binding.mainDrawerLayout,
//            binding.mainToolbar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        binding.mainDrawerLayout.addDrawerListener(actionBarDrawerToggle)
//        actionBarDrawerToggle.syncState()
//    }
}
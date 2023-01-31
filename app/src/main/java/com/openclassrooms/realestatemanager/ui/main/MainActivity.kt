package com.openclassrooms.realestatemanager.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.MainActivityBinding
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment
import com.openclassrooms.realestatemanager.ui.realEstateList.RealEstateListFragment
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // TODO need to take out overflow menu from toolBar

    private val binding by viewBinding { MainActivityBinding.inflate(it) }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setUpToolBarAndDrawerLayout()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.mainFragmentContainerViewRealEstateList.id, RealEstateListFragment.newInstance())
                .commit()
        }

        val containerDetailsId = binding.mainFragmentContainerViewDetail?.id
        if (containerDetailsId != null && supportFragmentManager.findFragmentById(containerDetailsId) == null) {
            supportFragmentManager.beginTransaction()
                .replace(containerDetailsId, DetailFragment())
                .commitNow()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()

//        viewModel.onResume(resources.getBoolean(R.bool.isTablet))
    }

    private fun setUpToolBarAndDrawerLayout() {
        setSupportActionBar(binding.mainMaterialToolbar)
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.mainDrawerLayout,
            binding.mainToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.mainDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }
}
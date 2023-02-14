package com.openclassrooms.realestatemanager.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.MainActivityBinding
import com.openclassrooms.realestatemanager.ui.add_real_estate.AddRealEstateActivity
import com.openclassrooms.realestatemanager.ui.detail.DetailActivity
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment
import com.openclassrooms.realestatemanager.ui.real_estate_list.RealEstateListFragment
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding { MainActivityBinding.inflate(it) }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setUpToolBarAndDrawerLayout()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    binding.mainFragmentContainerViewRealEstateList.id,
                    RealEstateListFragment.newInstance()
                )
                .commit()
        }

        val containerDetailsId = binding.mainFragmentContainerViewDetail?.id
        if (containerDetailsId != null && supportFragmentManager.findFragmentById(containerDetailsId) == null) {
            supportFragmentManager.beginTransaction()
                .replace(containerDetailsId, DetailFragment())
                .commitNow()
        }

        viewModel.viewActionSingleLiveEvent.observe(this) {
            when (it) {
                is MainViewAction.NavigateToDetailActivity -> startActivity(
                    Intent(
                        this,
                        DetailActivity::class.java
                    )
                )
                is MainViewAction.NavigateToAddRealEstateActivity -> startActivity(
                    AddRealEstateActivity.navigate(this, it.realEstateId)
                )
            }
        }

        // TODO Hangyeol this should no longer be needed
        // This Works
//        binding.mainToolbar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.main_toolbar_menu_create -> {
//                    viewModel.onToolBarMenuCreateClicked()
//                    true
//                }
//
//                R.id.main_toolbar_menu_modify -> {
//                    true
//                }
//
//                else -> {
//                    // If we got here, the user's action was not recognized.
//                    // Invoke the superclass to handle it.
//                    super.onOptionsItemSelected(menuItem)
//                }
//            }
//        }
    }


    override fun onResume() {
        super.onResume()

        viewModel.onResume(resources.getBoolean(R.bool.isTablet))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // TODO Hangyeol This should work in all 3 configurations
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.main_toolbar_menu_create -> {
            viewModel.onToolBarMenuCreateClicked()
            true
        }
        R.id.main_toolbar_menu_modify -> {
            true
        }
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        else -> super.onOptionsItemSelected(item)
    }


    private fun setUpToolBarAndDrawerLayout() {
        setSupportActionBar(binding.mainToolbar)
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
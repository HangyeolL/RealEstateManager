package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstateListFragmentBinding
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstateListFragment : Fragment(R.layout.real_estate_list_fragment), Toolbar.OnMenuItemClickListener {

    private val binding by viewBinding { RealEstateListFragmentBinding.bind(it) }

    private val viewModel by viewModels<RealEstateListViewModel>()

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_FragmentContainerView_navHost
        )
        val appBarConfiguration = AppBarConfiguration(navController.graph)


        val detailFragmentContainerView = binding.realEstateListLandFragmentContainerViewDetail

        // detailFragmentContainerView is inflated only in landscape so TRUE only in landscape
        if (detailFragmentContainerView != null) {
            childFragmentManager.beginTransaction()
                .replace(detailFragmentContainerView.id, DetailFragment())
                .commit()
        }

        // Navigation configuration in each fragment cause toolbar exists in each fragment !
        binding.realEstateListToolbar.setupWithNavController(navController, appBarConfiguration)
        binding.realEstateListToolbar.setOnMenuItemClickListener(this)

        val adapter = RealEstateListAdapter() {
            // Taking realEstateId from Adapter / Adapter is getting the id from ViewState
            navController.navigate(RealEstateListFragmentDirections.actionRealEstateListFragmentToDetailFragment(it))
        }

        binding.realEstateListRecyclerView.adapter = adapter

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it.itemViewStateList)
        }

    }

    override fun onMenuItemClick(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            R.id.toolbar_menu_create -> {
                navController.navigate(
                    RealEstateListFragmentDirections.actionRealEstateListFragmentToAddOrModifyRealEstateFragment()
                )
                true
            }

            else -> false
        }

    override fun onResume() {
        super.onResume()

    }

}
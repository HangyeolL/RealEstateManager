package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstateListFragmentBinding
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstateListFragment : Fragment(),
    Toolbar.OnMenuItemClickListener {

    private var _binding: RealEstateListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RealEstateListViewModel>()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var recyclerViewAdapter: RealEstateListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = RealEstateListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_FragmentContainerView_navHost
        )
        appBarConfiguration = AppBarConfiguration(navController.graph)

//        binding.realEstateListToolbar.setupWithNavController(navController, appBarConfiguration)
//        binding.realEstateListToolbar.setOnMenuItemClickListener(this)

        recyclerViewAdapter = RealEstateListAdapter() {
            binding.realEstateListSlidingPaneLayout.openPane()
        }

        binding.realEstateListRecyclerView.adapter = recyclerViewAdapter

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            recyclerViewAdapter.submitList(it.itemViewStateList)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
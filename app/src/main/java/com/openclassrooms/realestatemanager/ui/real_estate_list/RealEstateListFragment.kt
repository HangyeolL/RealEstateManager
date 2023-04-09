package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstateListFragmentBinding
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstateListFragment : Fragment(R.layout.real_estate_list_fragment),
    Toolbar.OnMenuItemClickListener {

    private val binding by viewBinding {  RealEstateListFragmentBinding.bind(it)}

    private val viewModel by viewModels<RealEstateListViewModel>()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_FragmentContainerView_navHost
        )
        appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.realEstateListSlidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
        // Connect the SlidingPaneLayout to the system back button.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            RealEstateListOnBackPressedCallback(binding.realEstateListSlidingPaneLayout)
        )

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.Toolbar)
        
        binding.realEstateListSlidingPaneLayout.addPanelSlideListener()

//        binding.realEstateListToolbar.setupWithNavController(navController, appBarConfiguration)
//        binding.realEstateListToolbar.setOnMenuItemClickListener(this)

        val recyclerViewAdapter = RealEstateListAdapter() { itemId ->
            viewModel.onRealEstateListItemClicked(itemId)
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
}

/**
 * Callback providing custom back navigation.
 */
class RealEstateListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout,
): OnBackPressedCallback(
    // Set the default 'enabled' state to true only if it is slidable (i.e., the panes
    // are overlapping) and open (i.e., the detail pane is visible).
    slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen
), SlidingPaneLayout.PanelSlideListener {

    override fun handleOnBackPressed() {
        // Return to the list pane when the system back button is pressed.
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {}

    override fun onPanelOpened(panel: View) {
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        isEnabled = false
    }



}
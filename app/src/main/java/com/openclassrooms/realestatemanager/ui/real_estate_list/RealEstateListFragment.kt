package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.os.Bundle
import android.util.Log
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
class RealEstateListFragment : Fragment(R.layout.real_estate_list_fragment) {

    private val binding by viewBinding { RealEstateListFragmentBinding.bind(it) }

    private val viewModel by viewModels<RealEstateListViewModel>()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

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

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.main_Toolbar)

        binding.realEstateListSlidingPaneLayout.addPanelSlideListener(object :
            SlidingPaneLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {}

            override fun onPanelOpened(panel: View) {
                toolbar.menu.clear()
                toolbar.inflateMenu(R.menu.detail_toolbar_menu)
            }

            override fun onPanelClosed(panel: View) {
                toolbar.menu.clear()
                toolbar.inflateMenu(R.menu.toolbar_menu)
            }

        })


        val recyclerViewAdapter = RealEstateListAdapter() { itemId ->
            viewModel.onRealEstateListItemClicked(itemId)
            binding.realEstateListSlidingPaneLayout.openPane()
        }

        binding.realEstateListRecyclerView.adapter = recyclerViewAdapter

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            recyclerViewAdapter.submitList(it.itemViewStateList)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_menu_modify -> {
                val realEstateId = viewModel.selectedRealEstateId
                Log.d("HL", "ListFragment handling toolBar menu modify")
                Log.d("HL", "ListToAddOrModify:currentRealEstateId=${realEstateId}")

                navController.navigate(
                    RealEstateListFragmentDirections.actionToAddOrModifyRealEstateFragment(
                        realEstateId
                    )
                )

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

/**
 * Callback providing custom back navigation.
 */
class RealEstateListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout,
) : OnBackPressedCallback(
    // Set the default 'enabled' state to true only if it is slidable (i.e., the panes
    // are overlapping) and open (i.e., the detail pane is visible).
    slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen
), SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

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
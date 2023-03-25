package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstateListFragmentBinding
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateActivity
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstateListFragment : Fragment(R.layout.real_estate_list_fragment) {

    companion object {
        fun newInstance() = RealEstateListFragment()
    }

    private val binding by viewBinding { RealEstateListFragmentBinding.bind(it) }

    private val viewModel by viewModels<RealEstateListViewModel>()

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_FragmentContainerView_navHost
        )

        val adapter = RealEstateListAdapter() {
            // Taking realEstateId from Adapter / Adapter is getting the id from ViewState
            navController.navigate(RealEstateListFragmentDirections.actionRealEstateListFragmentToDetailFragment(it))
        }

        binding.realEstateListRecyclerView.adapter = adapter

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it.itemViewStateList)
        }

//        viewModel.viewActionSingleLiveEvent.observe(viewLifecycleOwner) { viewAction ->
//            when(viewAction) {
//                is RealEstateListViewAction.NavigateToAddOrModifyRealEstateActivity ->
//                    startActivity(AddOrModifyRealEstateActivity.navigate(requireContext(), viewAction.realEstateId))
//            }
//        }


    }

    override fun onResume() {
        super.onResume()

        val detailFragmentContainerView = binding.realEstateListLandFragmentContainerViewDetail

        // detailFragmentContainerView is inflated only in landscape so TRUE only in landscape
        if (detailFragmentContainerView != null) {
            childFragmentManager.beginTransaction()
                .replace(detailFragmentContainerView.id, DetailFragment())
                .commit()
        }
    }

}
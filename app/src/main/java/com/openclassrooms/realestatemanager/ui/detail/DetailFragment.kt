package com.openclassrooms.realestatemanager.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.DetailFragmentBinding
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoListAdapter
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoListPagingIndicationDecoration
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val binding by viewBinding { DetailFragmentBinding.bind(it) }

    private val viewModel by viewModels<DetailViewModel>()

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_FragmentContainerView_navHost
        )

        val adapter = RealEstatePhotoListAdapter() {

        }
        binding.detailRecyclerViewImages.adapter = adapter

        // add pager behavior to RecyclerView
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.detailRecyclerViewImages)
        binding.detailRecyclerViewImages.addItemDecoration(RealEstatePhotoListPagingIndicationDecoration())

        viewModel.detailViewStateLiveData.observe(viewLifecycleOwner) { detailViewState ->

            binding.detailConstraintLayoutParent.isVisible = detailViewState.isViewVisible

            binding.detailTextViewDescriptionBody.text = detailViewState.descriptionBody
            binding.detailTextViewSquareMeter.text = detailViewState.squareMeter.toString()
            binding.detailTextViewRooms.text = detailViewState.numberOfRooms.toString()
            binding.detailTextViewBathrooms.text = detailViewState.numberOfBathrooms.toString()
            binding.detailTextViewBedrooms.text = detailViewState.numberOfBedrooms.toString()
            binding.detailTextViewAddress.text = detailViewState.address
            binding.detailTextViewAgentName.text = detailViewState.agentName

            adapter.submitList(detailViewState.itemViewStateList)
        }
    }

    override fun onResume() {
        super.onResume()

        if (activity?.resources?.getBoolean(R.bool.isTablet) == true) {
            navController.navigateUp()
        }
    }

}

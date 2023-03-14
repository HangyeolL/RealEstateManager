package com.openclassrooms.realestatemanager.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.DetailFragmentBinding
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoListAdapter
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val binding by viewBinding { DetailFragmentBinding.bind(it) }

    private val viewModel by viewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RealEstatePhotoListAdapter()

        viewModel.mediatorFlow.observe(viewLifecycleOwner) {

            binding.detailConstraintLayoutParent.isVisible = it.isViewVisible

            binding.detailTextViewDescriptionBody.text = it.descriptionBody
            binding.detailTextViewSquareMeter.text = it.squareMeter.toString()
            binding.detailTextViewRooms.text = it.numberOfRooms.toString()
            binding.detailTextViewBathrooms.text = it.numberOfBathrooms.toString()
            binding.detailTextViewBedrooms.text = it.numberOfBedrooms.toString()
            binding.detailTextViewAddress.text = it.address
            binding.detailTextViewAgentName.text = it.agentName

            binding.detailRecyclerViewImages.adapter = adapter
            adapter.submitList(it.itemViewStateList)
        }
    }

//        val requestPermissionLauncher =
//            registerForActivityResult(
//                ActivityResultContracts.RequestPermission()
//            ) { isGranted: Boolean ->
//                if (isGranted) {
//                    viewModel.startLocationRequest()
//                } else {
//                    viewModel.stopLocationRequest()
//                }
//            }
//


}

package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.design_system.photo_carousel.RealEstatePhotoListAdapter
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddOrModifyRealEstateFragment : Fragment(R.layout.add_or_modify_real_estate_fragment) {

    companion object {
        const val KEY_REAL_ESTATE_ID = "KEY_REAL_ESTATE_ID"

        fun newInstance(realEstateId: Int?) = AddOrModifyRealEstateFragment().apply {
            arguments = Bundle().apply {
                realEstateId?.let { putInt(KEY_REAL_ESTATE_ID, realEstateId) }
            }
        }
    }

    private val binding by viewBinding { com.openclassrooms.realestatemanager.databinding.AddOrModifyRealEstateFragmentBinding.bind(it) }

    private val viewModel by viewModels<AddOrModifyRealEstateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val typeSpinnerAdapter = AddOrModifyRealEstateTypeSpinnerAdapter(requireContext(), R.layout.add_real_estate_spinner_item)
        val agentSpinnerAdapter = AddOrModifyRealEstateAgentSpinnerAdapter(requireContext(), R.layout.add_real_estate_spinner_item)
        val realEstatePhotoListAdapter = RealEstatePhotoListAdapter()

        binding.addOrModifyRealEstateAutoCompleteTextViewAsTypeSpinner.setAdapter(typeSpinnerAdapter)
        binding.addOrModifyRealEstateAutoCompleteTextViewAsAgentSpinner.setAdapter(agentSpinnerAdapter)
        binding.addOrModifyRealEstateRecyclerViewRealEstatePhotoList.adapter = realEstatePhotoListAdapter

        viewModel.mediatorFlow.observe(viewLifecycleOwner) {
            typeSpinnerAdapter.addAll(it.typeSpinnerItemViewStateList)
            agentSpinnerAdapter.addAll(it.agentSpinnerItemViewStateList)
            realEstatePhotoListAdapter.submitList(it.realEstatePhotoListItemViewStateList)

        }
    }
}
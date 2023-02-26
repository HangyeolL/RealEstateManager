package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.PredictionResponse
import com.openclassrooms.realestatemanager.databinding.AddOrModifyRealEstateFragmentBinding
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

    private val binding by viewBinding { AddOrModifyRealEstateFragmentBinding.bind(it) }

    private val viewModel by viewModels<AddOrModifyRealEstateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val typeSpinnerAdapter = AddOrModifyRealEstateTypeSpinnerAdapter(
            requireContext(),
            R.layout.add_real_estate_spinner_item
        )
        val agentSpinnerAdapter = AddOrModifyRealEstateAgentSpinnerAdapter(
            requireContext(),
            R.layout.add_real_estate_spinner_item
        )
        val realEstatePhotoListAdapter = RealEstatePhotoListAdapter()

        binding.addOrModifyRealEstateAutoCompleteTextViewAsTypeSpinner.setAdapter(typeSpinnerAdapter)
        binding.addOrModifyRealEstateAutoCompleteTextViewAsAgentSpinner.setAdapter(agentSpinnerAdapter)
        binding.addOrModifyRealEstateRecyclerViewRealEstatePhotoList.adapter = realEstatePhotoListAdapter

        viewModel.mediatorFlow.observe(viewLifecycleOwner) {

            typeSpinnerAdapter.addAll(it.typeSpinnerItemViewStateList)
            agentSpinnerAdapter.addAll(it.agentSpinnerItemViewStateList)
            realEstatePhotoListAdapter.submitList(it.realEstatePhotoListItemViewStateList)

            binding.addOrModifyRealEstateTextInputEditTextNumberOfRooms.setText(it.numberOfRooms.toString())
            binding.addOrModifyRealEstateTextInputEditTextNumberOfBedRooms.setText(it.numberOfBedrooms.toString())
            binding.addOrModifyRealEstateTextInputEditTextNumberOfBathRooms.setText(it.numberOfBathrooms.toString())

            binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setText(it.address)
            binding.addOrModifyRealEstateTextInputEditTextDescriptionBody.setText(it.description)
            binding.addOrModifyRealEstateTextInputEditTextMarketSince.setText(it.marketSince)
            binding.addOrModifyRealEstateTextInputEditTextSoldOutDate.setText(it.dateOfSold)
            binding.addOrModifyRealEstateTextInputEditTextPrice.setText(it.price.toString())
            binding.addOrModifyRealEstateTextInputEditTextSqm.setText(it.squareMeter.toString())

            binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setAdapter(
                ArrayAdapter<PredictionResponse>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    it.addressAutocompletePredictions
                )
            )

        }



        binding.addOrModifyRealEstateAutoCompleteTextViewAddress.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
                viewModel.onEditTextAddressChanged(text.toString())

            }
        })

    }

}
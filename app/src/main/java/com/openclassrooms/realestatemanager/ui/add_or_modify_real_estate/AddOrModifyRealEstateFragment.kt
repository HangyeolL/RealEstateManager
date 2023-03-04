package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
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
            R.layout.add_or_modify_real_estate_spinner_item
        )
        val agentSpinnerAdapter = AddOrModifyRealEstateAgentSpinnerAdapter(
            requireContext(),
            R.layout.add_or_modify_real_estate_spinner_item
        )
        val realEstatePhotoListAdapter = RealEstatePhotoListAdapter()

        binding.addOrModifyRealEstateAutoCompleteTextViewAsTypeSpinner.setAdapter(typeSpinnerAdapter)
        binding.addOrModifyRealEstateAutoCompleteTextViewAsAgentSpinner.setAdapter(
            agentSpinnerAdapter
        )
        binding.addOrModifyRealEstateRecyclerViewRealEstatePhotoList.adapter =
            realEstatePhotoListAdapter

        viewModel.initialViewStateLiveData.observe(viewLifecycleOwner) {

            typeSpinnerAdapter.clear()
            typeSpinnerAdapter.addAll(it.typeSpinnerItemViewStateList)

            agentSpinnerAdapter.clear()
            agentSpinnerAdapter.addAll(it.agentSpinnerItemViewStateList)

            realEstatePhotoListAdapter.submitList(it.realEstatePhotoListItemViewStateList)

            binding.addOrModifyRealEstateTextInputEditTextNumberOfRooms.setText(it.numberOfRooms)
            binding.addOrModifyRealEstateTextInputEditTextNumberOfBedRooms.setText(it.numberOfBedrooms)
            binding.addOrModifyRealEstateTextInputEditTextNumberOfBathRooms.setText(it.numberOfBathrooms)

            binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setText(it.address)
            binding.addOrModifyRealEstateTextInputEditTextDescriptionBody.setText(it.description)
            binding.addOrModifyRealEstateTextInputEditTextMarketSince.setText(it.marketSince)
            binding.addOrModifyRealEstateTextInputEditTextDateOfSold.setText(it.dateOfSold)
            binding.addOrModifyRealEstateTextInputEditTextPrice.setText(it.price)
            binding.addOrModifyRealEstateTextInputEditTextSqm.setText(it.squareMeter)
        }

        viewModel.addressPredictionsLiveData.observe(viewLifecycleOwner) { addressAutocompletePredictions ->
            binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    addressAutocompletePredictions
                )
            )
        }

        viewModel.stringSingleLiveEvent.observe(viewLifecycleOwner) { string ->
            Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
        }

        viewModel.intentSingleLiveEvent.observe(viewLifecycleOwner) { intent ->
            startActivity(intent)
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewAsTypeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.onTypeSpinnerItemSelected(adapter?.selectedItem as AddOrModifyRealEstateTypeSpinnerItemViewState)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }


        binding.addOrModifyRealEstateAutoCompleteTextViewAddress.addTextChangedListener {
            viewModel.onEditTextAddressChanged(it?.toString())
        }

        binding.addOrModifyRealEstateTextInputEditTextCity.addTextChangedListener {
            viewModel.onEditTextCityChanged(it?.toString())
        }

        binding.addOrModifyRealEstateTextInputEditTextNumberOfRooms.addTextChangedListener {
            viewModel.onEditTextNumberOfRoomsChanged(it?.toString())
        }

        binding.addOrModifyRealEstateTextInputEditTextNumberOfBedRooms.addTextChangedListener {
            viewModel.onEditTextNumberOfBedRoomsChanged(it?.toString())
        }

        binding.addOrModifyRealEstateTextInputEditTextNumberOfBathRooms.addTextChangedListener {
            viewModel.onEditTextNumberOfBathRoomsChanged(it?.toString())
        }

        binding.addOrModifyRealEstateTextInputEditTextSqm.addTextChangedListener {
            viewModel.onEditTextSqmChanged(it?.toString())
        }

        binding.addOrModifyRealEstateTextInputEditTextMarketSince.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("DATE", 1)

            DatePickerDialogFragment().arguments = bundle
            DatePickerDialogFragment().show(childFragmentManager, "datePicker")
        }

        binding.addOrModifyRealEstateTextInputEditTextPrice.addTextChangedListener {
            viewModel.onEditTextPriceChanged(it?.toString())
        }

        binding.addOrModifyRealEstateChipGuard.setOnClickListener {
            viewModel.onChipGuardClicked(binding.addOrModifyRealEstateChipGuard.isChecked)
        }

        binding.addOrModifyRealEstateChipGarage.setOnClickListener {
            viewModel.onChipGarageClicked(binding.addOrModifyRealEstateChipGarage.isChecked)
        }

        binding.addOrModifyRealEstateChipGarden.setOnClickListener {
            viewModel.onChipGardenClicked(binding.addOrModifyRealEstateChipGarden.isChecked)
        }

        binding.addOrModifyRealEstateChipElevator.setOnClickListener {
            viewModel.onChipElevatorClicked(binding.addOrModifyRealEstateChipElevator.isChecked)
        }

        binding.addOrModifyRealEstateChipGroceryStoreNextBy.setOnClickListener {
            viewModel.onChipGroceryStoreNextByClicked(binding.addOrModifyRealEstateChipGroceryStoreNextBy.isChecked)
        }

        binding.addOrModifyRealEstateChipIsSoldOut.setOnClickListener {
            viewModel.onChipIsSoldOutClicked(binding.addOrModifyRealEstateChipIsSoldOut.isChecked)
        }

        binding.addOrModifyRealEstateTextInputEditTextDateOfSold.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("DATE", 2)

            DatePickerDialogFragment().arguments = bundle
            DatePickerDialogFragment().show(childFragmentManager, "datePicker")
        }

        binding.addOrModifyRealEstateTextInputEditTextDescriptionBody.addTextChangedListener {
            viewModel.onEditTextDescriptionChanged(it?.toString())
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewAsAgentSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.onAgentSpinnerItemSelected(adapter?.selectedItem as AddOrModifyRealEstateAgentSpinnerItemViewState)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

        binding.addOrModifyRealEstateButtonSave.setOnClickListener {
            viewModel.onSaveButtonClicked()
        }

    }

}
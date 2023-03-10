package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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

        // Camera permission request TODO how to do this in ViewModel
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {

                } else {

                }
            }

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) -> {

            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        val typeSpinnerAdapter = AddOrModifyRealEstateTypeSpinnerAdapter(
            requireContext(),
            R.layout.add_or_modify_real_estate_spinner_item
        )
        val agentSpinnerAdapter = AddOrModifyRealEstateAgentSpinnerAdapter(
            requireContext(),
            R.layout.add_or_modify_real_estate_spinner_item
        )
        val realEstatePhotoListAdapter = RealEstatePhotoListAdapter()
        val autocompleteAdapter = AddOrModifyRealEstateAddressAutocompleteAdapter()

        binding.addOrModifyRealEstateAutoCompleteTextViewAsTypeSpinner.setAdapter(typeSpinnerAdapter)
        binding.addOrModifyRealEstateAutoCompleteTextViewAsAgentSpinner.setAdapter(agentSpinnerAdapter)
        binding.addOrModifyRealEstateRecyclerViewRealEstatePhotoList.adapter = realEstatePhotoListAdapter
        binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setAdapter(autocompleteAdapter)
        binding.addOrModifyRealEstateAutoCompleteTextViewCity.setAdapter(autocompleteAdapter)

        viewModel.initialViewStateLiveData.observe(viewLifecycleOwner) { viewState ->

            typeSpinnerAdapter.clear()
            typeSpinnerAdapter.addAll(viewState.typeSpinnerItemViewStateList)

            agentSpinnerAdapter.clear()
            agentSpinnerAdapter.addAll(viewState.agentSpinnerItemViewStateList)

            realEstatePhotoListAdapter.submitList(viewState.realEstatePhotoListItemViewStateList)

            binding.addOrModifyRealEstateTextInputEditTextNumberOfRooms.setText(viewState.numberOfRooms)
            binding.addOrModifyRealEstateTextInputEditTextNumberOfBedRooms.setText(viewState.numberOfBedrooms)
            binding.addOrModifyRealEstateTextInputEditTextNumberOfBathRooms.setText(viewState.numberOfBathrooms)

            binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setText(viewState.address)
            binding.addOrModifyRealEstateAutoCompleteTextViewCity.setText(viewState.city)

            binding.addOrModifyRealEstateTextInputEditTextDescriptionBody.setText(viewState.description)
            binding.addOrModifyRealEstateTextInputEditTextMarketSince.setText(viewState.marketSince)
            binding.addOrModifyRealEstateTextInputEditTextDateOfSold.setText(viewState.dateOfSold)
            binding.addOrModifyRealEstateTextInputEditTextPrice.setText(viewState.price)
            binding.addOrModifyRealEstateTextInputEditTextSqm.setText(viewState.squareMeter)
        }

        viewModel.addressPredictionsLiveData.observe(viewLifecycleOwner) { viewStates ->
            autocompleteAdapter.setData(viewStates)
        }

        viewModel.cityPredictionsLiveData.observe(viewLifecycleOwner) { viewStates ->
            autocompleteAdapter.setData(viewStates)
        }

        viewModel.stringSingleLiveEvent.observe(viewLifecycleOwner) { string ->
            Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
        }

        viewModel.intentSingleLiveEvent.observe(viewLifecycleOwner) { intent ->
            startActivity(intent)
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewAsTypeSpinner.setOnItemClickListener { _, _, position, _ ->
            typeSpinnerAdapter.getItem(position)?.let { typeSpinnerItemViewState ->
                viewModel.onTypeSpinnerItemClicked(typeSpinnerItemViewState)
            }
        }


        binding.addOrModifyRealEstateAutoCompleteTextViewAddress.addTextChangedListener {
            viewModel.onEditTextAddressChanged(it?.toString())
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewCity.addTextChangedListener {
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

        viewModel.onDefaultMarketSinceValueSet(binding.addOrModifyRealEstateTextInputEditTextMarketSince.text.toString())

        binding.addOrModifyRealEstateTextInputEditTextMarketSince.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("DATE", 1)

            val datePicker = DatePickerDialogFragment { p0, year, month, day ->
                viewModel.onUserMarketSinceDateSet(year, month, day)
                binding.addOrModifyRealEstateTextInputEditTextMarketSince.setText("$day/$month/$year")
            }
            datePicker.arguments = bundle
            datePicker.show(childFragmentManager, "datePicker")
        }

//        val marketSinceDatePicker = object: DatePickerDialog.OnDateSetListener {
//            override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
//
//            }
//        }

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

        val dateOfSoldDatePicker =
            DatePickerDialog.OnDateSetListener { p0, year, month, day ->
                viewModel.onUserDateOfSoldSet(year, month, day)
                binding.addOrModifyRealEstateTextInputEditTextDateOfSold.setText("$day/$month/$year")
            }

        binding.addOrModifyRealEstateTextInputEditTextDateOfSold.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("DATE", 2)

            val datePicker = DatePickerDialogFragment(dateOfSoldDatePicker)
            datePicker.arguments = bundle
            datePicker.show(childFragmentManager, "datePicker")
        }

        binding.addOrModifyRealEstateTextInputEditTextDescriptionBody.addTextChangedListener {
            viewModel.onEditTextDescriptionChanged(it?.toString())
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewAsAgentSpinner.setOnItemClickListener { _, _, position, _ ->
            agentSpinnerAdapter.getItem(position)?.let { agentSpinnerItemViewState ->
                viewModel.onAgentSpinnerItemClicked(agentSpinnerItemViewState)
            }
        }

        binding.addOrModifyRealEstateButtonSave.setOnClickListener {
            viewModel.onSaveButtonClicked()
        }

    }

}
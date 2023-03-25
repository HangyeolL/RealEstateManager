package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddOrModifyRealEstateFragmentBinding
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoListAdapter
import com.openclassrooms.realestatemanager.ui.add_photo.AddPhotoDialogFragment
import com.openclassrooms.realestatemanager.ui.date_picker.DatePickerDialogFragment
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

//TODO back button on Toolbar doesnt appear

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

    private var realEstateId: Int? = null
    private var latestTmpUri: Uri? = null

    private val takePictureResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            val addPhotoDialogFragment = AddPhotoDialogFragment()
            val bundle = Bundle()

            latestTmpUri.let { uri ->
//                val capturedRealEstateId = realEstateId

                addPhotoDialogFragment.arguments = bundle.apply {
                    putString("PICTURE", uri.toString())
                }
            }
            realEstateId?.let { int ->
                addPhotoDialogFragment.arguments = bundle.apply {
                    putInt(KEY_REAL_ESTATE_ID, int)
                }
            }

            addPhotoDialogFragment.arguments = bundle
            addPhotoDialogFragment.show(childFragmentManager, "addPhotoDialog")
        }
    }

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (this.arguments != null) {
            realEstateId = this.requireArguments().getInt("KEY_REAL_ESTATE_ID")
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
        val autocompleteAdapter = AddOrModifyRealEstateAutocompleteAdapter()

        binding.addOrModifyRealEstateAutoCompleteTextViewAsTypeSpinner.setAdapter(typeSpinnerAdapter)
        binding.addOrModifyRealEstateAutoCompleteTextViewAsAgentSpinner.setAdapter(agentSpinnerAdapter)
        binding.addOrModifyRealEstateRecyclerViewRealEstatePhotoList.adapter = realEstatePhotoListAdapter
        binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setAdapter(autocompleteAdapter)
        binding.addOrModifyRealEstateAutoCompleteTextViewCity.setAdapter(autocompleteAdapter)

        /**
         * Observer set up
         */
        viewModel.initialViewStateLiveData.observe(viewLifecycleOwner) { viewState ->

            typeSpinnerAdapter.clear()
            typeSpinnerAdapter.addAll(viewState.typeSpinnerItemViewStateList)

            agentSpinnerAdapter.clear()
            agentSpinnerAdapter.addAll(viewState.agentSpinnerItemViewStateList)

            realEstatePhotoListAdapter.submitList(viewState.realEstatePhotoListItemViewStateList)

            binding.addOrModifyRealEstateTextInputEditTextNumberOfRooms.setText(viewState.numberOfRooms)
            binding.addOrModifyRealEstateTextInputEditTextNumberOfBedRooms.setText(viewState.numberOfBedrooms)
            binding.addOrModifyRealEstateTextInputEditTextNumberOfBathRooms.setText(viewState.numberOfBathrooms)
            binding.addOrModifyRealEstateTextInputEditTextPrice.setText(viewState.price)
            binding.addOrModifyRealEstateTextInputEditTextSqm.setText(viewState.squareMeter)

            binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setText(viewState.address)
            binding.addOrModifyRealEstateAutoCompleteTextViewCity.setText(viewState.city)

            binding.addOrModifyRealEstateTextInputEditTextDescriptionBody.setText(viewState.description)

            binding.addOrModifyRealEstateTextInputEditTextMarketSince.setText(viewState.marketSince)
            viewModel.onDefaultMarketSinceValueSet(binding.addOrModifyRealEstateTextInputEditTextMarketSince.text.toString())

            binding.addOrModifyRealEstateTextInputEditTextDateOfSold.setText(viewState.dateOfSold)
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

        viewModel.viewActionSingleLiveEvent.observe(viewLifecycleOwner) { viewAction ->
            when (viewAction) {
                is AddOrModifyRealEstateViewAction.OpenCamera ->
                    getTmpFileUri().let { uri ->
                        latestTmpUri = uri
                        takePictureResult.launch(uri)
                    }

                is AddOrModifyRealEstateViewAction.NavigateToMainActivity ->
                    startActivity(MainActivity.navigate(requireContext()))
            }
        }

        /**
         * Listener set up
         */

        binding.addOrModifyRealEstateAutoCompleteTextViewAsTypeSpinner.setOnItemClickListener { _, _, position, _ ->
            typeSpinnerAdapter.getItem(position)?.let { typeSpinnerItemViewState ->
                viewModel.onTypeSpinnerItemClicked(typeSpinnerItemViewState)
            }
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewAddress.addTextChangedListener {
            viewModel.onAutocompleteAddressChanged(it?.toString())
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setOnItemClickListener { adapterView, view, position, l ->
            autocompleteAdapter.getItem(position)?.let { addressAutocompleteItemViewState ->
                viewModel.onAutocompleteAddressItemClicked(addressAutocompleteItemViewState)
            }
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewCity.addTextChangedListener {
            viewModel.onEditTextCityChanged(it?.toString())
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewCity.setOnItemClickListener { adapterView, view, position, l ->
            autocompleteAdapter.getItem(position)?.let { addressAutocompleteItemViewState ->
                viewModel.onAutocompleteCityItemClicked(addressAutocompleteItemViewState)
            }
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

            val datePicker = DatePickerDialogFragment { p0, year, month, day ->
                viewModel.onUserMarketSinceDateSet(year, month, day)
                binding.addOrModifyRealEstateTextInputEditTextMarketSince.setText("$day/$month/$year")
            }
            datePicker.arguments = bundle
            datePicker.show(childFragmentManager, "datePicker")
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

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", requireContext().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }

}
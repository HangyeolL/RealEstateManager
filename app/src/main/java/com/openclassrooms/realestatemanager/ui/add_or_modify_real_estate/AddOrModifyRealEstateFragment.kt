package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddOrModifyRealEstateFragmentBinding
import com.openclassrooms.realestatemanager.design_system.autocomplete_text_view.AutocompleteAdapter
import com.openclassrooms.realestatemanager.design_system.real_estate_agent.RealEstateAgentSpinnerAdapter
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoListAdapter
import com.openclassrooms.realestatemanager.design_system.real_estate_type.RealEstateTypeSpinnerAdapter
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class AddOrModifyRealEstateFragment : Fragment(R.layout.add_or_modify_real_estate_fragment) {

    companion object {
        private const val MARKET_SINCE = 1
        private const val DATE_OF_SOLD = 2
    }

    private val binding by viewBinding { AddOrModifyRealEstateFragmentBinding.bind(it) }
    private val viewModel by viewModels<AddOrModifyRealEstateViewModel>()

    private lateinit var navController: NavController
    private val args: AddOrModifyRealEstateFragmentArgs by navArgs()

    private var latestTmpUri: Uri? = null

    private val intentAppChooserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedIntent = result.data

                if (selectedIntent?.data != null) {
                    // The user selected an image from the gallery
                    Log.d("HL", "User chose gallery for an image")
                    navController.navigate(
                        AddOrModifyRealEstateFragmentDirections.actionToAddPhotoDialogFragment(
                            args.realEstateId, selectedIntent.data.toString()
                        )
                    )
                }
                // Camera to capture an image
                else if (selectedIntent?.extras != null) {
                    Log.d("HL", "User chose camera for an image")
                    Log.d("HL", "latestTmpUri: $latestTmpUri")
                    navController.navigate(
                        AddOrModifyRealEstateFragmentDirections.actionToAddPhotoDialogFragment(
                            args.realEstateId, latestTmpUri.toString()
                        )
                    )
                }
            }
        }

    private fun getTmpFileUri(): Uri {
        val tmpFile =
            File.createTempFile("tmp_image_file", ".png", requireContext().cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    // Function to show an intent chooser between camera and gallery
    private fun showImageSourceChooser() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)

        // Decide where the capture image should be saved
        latestTmpUri = getTmpFileUri()
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, latestTmpUri)
        // Restrict to only image types
        galleryIntent.type = "image/*"

        val chooserIntent = Intent.createChooser(galleryIntent, "Choose image source")
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            arrayOf(cameraIntent)
        )

        intentAppChooserLauncher.launch(chooserIntent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_FragmentContainerView_navHost
        )

        // Adapter set up
        val typeSpinnerAdapter = RealEstateTypeSpinnerAdapter(
            requireContext(),
            R.layout.add_or_modify_real_estate_spinner_item
        )
        val agentSpinnerAdapter = RealEstateAgentSpinnerAdapter(
            requireContext(),
            R.layout.add_or_modify_real_estate_spinner_item
        )
        val realEstatePhotoListAdapter = RealEstatePhotoListAdapter {
            latestTmpUri = getTmpFileUri()
            showImageSourceChooser()
        }
        val autocompleteAdapter = AutocompleteAdapter()

        binding.addOrModifyRealEstateAutoCompleteTextViewAsTypeSpinner.setAdapter(typeSpinnerAdapter)
        binding.addOrModifyRealEstateAutoCompleteTextViewAsAgentSpinner.setAdapter(
            agentSpinnerAdapter
        )
        binding.addOrModifyRealEstateRecyclerViewRealEstatePhotoList.adapter =
            realEstatePhotoListAdapter
        binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setAdapter(autocompleteAdapter)
        binding.addOrModifyRealEstateAutoCompleteTextViewCity.setAdapter(autocompleteAdapter)

        // Observer for viewState set up
        viewModel.initialViewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is AddOrModifyRealEstateViewState.Modification -> {
                    typeSpinnerAdapter.clear()
                    typeSpinnerAdapter.addAll(viewState.typeSpinnerItemViewStateList)

                    agentSpinnerAdapter.clear()
                    agentSpinnerAdapter.addAll(viewState.agentSpinnerItemViewStateList)

                    realEstatePhotoListAdapter.submitList(viewState.realEstatePhotoListItemViewStateList)

                    binding.addOrModifyRealEstateTextInputEditTextNumberOfRooms.setText(viewState.numberOfRooms)
                    binding.addOrModifyRealEstateTextInputEditTextNumberOfBedRooms.setText(viewState.numberOfBedrooms)
                    binding.addOrModifyRealEstateTextInputEditTextNumberOfBathRooms.setText(
                        viewState.numberOfBathrooms
                    )
                    binding.addOrModifyRealEstateTextInputEditTextPrice.setText(viewState.price)
                    binding.addOrModifyRealEstateTextInputEditTextSqm.setText(viewState.squareMeter)

                    binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setText(viewState.address)
                    binding.addOrModifyRealEstateAutoCompleteTextViewCity.setText(viewState.city)

                    binding.addOrModifyRealEstateTextInputEditTextDescriptionBody.setText(viewState.description)

                    binding.addOrModifyRealEstateTextInputEditTextMarketSince.setText(viewState.marketSince)
                    viewModel.onDefaultMarketSinceValueSet(binding.addOrModifyRealEstateTextInputEditTextMarketSince.text.toString())

                    binding.addOrModifyRealEstateTextInputEditTextDateOfSold.setText(viewState.dateOfSold)
                }
                is AddOrModifyRealEstateViewState.Creation -> {
                    typeSpinnerAdapter.clear()
                    typeSpinnerAdapter.addAll(viewState.typeSpinnerItemViewStateList)

                    agentSpinnerAdapter.clear()
                    agentSpinnerAdapter.addAll(viewState.agentSpinnerItemViewStateList)

                    realEstatePhotoListAdapter.submitList(viewState.realEstatePhotoListItemViewStateList)

                    viewModel.onDefaultMarketSinceValueSet(binding.addOrModifyRealEstateTextInputEditTextMarketSince.text.toString())
                }
            }
        }

        // Observer : Autocomplete dynamic liveData
        viewModel.addressPredictionsLiveData.observe(viewLifecycleOwner) { viewState ->
            autocompleteAdapter.setData(viewState)
        }

        viewModel.cityPredictionsLiveData.observe(viewLifecycleOwner) { viewState ->
            autocompleteAdapter.setData(viewState)
        }

        // Observer : NavController retrieving data from DatePickerDialogFragment
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("MarketSince")
            ?.observe(viewLifecycleOwner) { pickedDate ->
                viewModel.onUserMarketSinceDateSet(pickedDate)
                binding.addOrModifyRealEstateTextInputEditTextMarketSince.setText(pickedDate)
            }

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("DateOfSold")
            ?.observe(viewLifecycleOwner) { pickedDate ->
                viewModel.onUserDateOfSoldSet(pickedDate)
                binding.addOrModifyRealEstateTextInputEditTextDateOfSold.setText(pickedDate)
            }

        //  Observer: NavController retrieving data from AddPhotoFragment
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("picUriToString")
            ?.observe(viewLifecycleOwner) { picUriToString ->
                viewModel.onPicUriToStringSet(picUriToString)
            }

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("photoDescription")
            ?.observe(viewLifecycleOwner) { photoDescription ->
                viewModel.photoDescriptionSet(photoDescription)
            }


        // Listener set up //
        binding.addOrModifyRealEstateAutoCompleteTextViewAsTypeSpinner.setOnItemClickListener { _, _, position, _ ->
            typeSpinnerAdapter.getItem(position)?.let { typeSpinnerItemViewState ->
                viewModel.onTypeSpinnerItemClicked(typeSpinnerItemViewState)
            }
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewAddress.addTextChangedListener {
            viewModel.onAutocompleteAddressChanged(it?.toString())
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setOnItemClickListener { _, _, position, _ ->
            autocompleteAdapter.getItem(position)?.let { autocompleteTextViewState ->
                viewModel.onAutocompleteAddressItemClicked(autocompleteTextViewState)
            }
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewCity.addTextChangedListener {
            viewModel.onEditTextCityChanged(it?.toString())
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewCity.setOnItemClickListener { _, _, position, _ ->
            autocompleteAdapter.getItem(position)?.let { autocompleteTextViewState ->
                viewModel.onAutocompleteCityItemClicked(autocompleteTextViewState)
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
            navController.navigate(
                AddOrModifyRealEstateFragmentDirections.actionToDatePickerDialogFragment(
                    MARKET_SINCE
                )
            )
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
            navController.navigate(
                AddOrModifyRealEstateFragmentDirections.actionToDatePickerDialogFragment(
                    DATE_OF_SOLD
                )
            )
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
            viewModel.onSaveButtonClicked {
                navController.popBackStack()
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()

    }


}
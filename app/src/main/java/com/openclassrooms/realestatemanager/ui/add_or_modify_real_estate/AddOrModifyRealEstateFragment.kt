package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
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

    // Intent to takePicture suing registerForActivityResult
    private var latestTmpUri: Uri? = null

    private val takePictureResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                navController.navigate(
                    AddOrModifyRealEstateFragmentDirections.actionToAddPhotoDialogFragment(
                        args.realEstateId, latestTmpUri.toString()
                    )
                )
            }
        }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {

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
        val realEstatePhotoListAdapter = RealEstatePhotoListAdapter() {
            latestTmpUri = getTmpFileUri()
            takePictureResult.launch(latestTmpUri)
        }
        val cityAutocompleteAdapter = AutocompleteAdapter()

        binding.addOrModifyRealEstateAutoCompleteTextViewAsTypeSpinner.setAdapter(typeSpinnerAdapter)
        binding.addOrModifyRealEstateAutoCompleteTextViewAsAgentSpinner.setAdapter(
            agentSpinnerAdapter
        )
        binding.addOrModifyRealEstateRecyclerViewRealEstatePhotoList.adapter =
            realEstatePhotoListAdapter
        binding.addOrModifyRealEstateAutoCompleteTextViewAddress.setAdapter(cityAutocompleteAdapter)
        binding.addOrModifyRealEstateAutoCompleteTextViewCity.setAdapter(cityAutocompleteAdapter)

        //TODO ViewState Observed data still emit the values after user puts the new value

        // Observer for viewState set up
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

        // Observer : Autocomplete dynamic liveData
        viewModel.addressPredictionsLiveData.observe(viewLifecycleOwner) { viewState ->
            cityAutocompleteAdapter.setData(viewState)
        }

        viewModel.cityPredictionsLiveData.observe(viewLifecycleOwner) { viewState ->
            cityAutocompleteAdapter.setData(viewState)
        }

        // Observer : SingleLiveEvent for Toast
        viewModel.stringSingleLiveEvent.observe(viewLifecycleOwner) { string ->
            Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
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

//         Observer: NavController retrieving data from AddPhotoFragment
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
            cityAutocompleteAdapter.getItem(position)?.let { autocompleteTextViewState ->
                viewModel.onAutocompleteAddressItemClicked(autocompleteTextViewState)
            }
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewCity.addTextChangedListener {
            viewModel.onEditTextCityChanged(it?.toString())
        }

        binding.addOrModifyRealEstateAutoCompleteTextViewCity.setOnItemClickListener { _, _, position, _ ->
            cityAutocompleteAdapter.getItem(position)?.let { autocompleteTextViewState ->
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
            viewModel.onSaveButtonClicked() {
                navController.popBackStack()
            }
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()

    }
}
package com.openclassrooms.realestatemanager.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.SearchModalBottomSheetsFragmentBinding
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateAgentSpinnerAdapter
import com.openclassrooms.realestatemanager.design_system.autocomplete_text_view.AutocompleteAdapter
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateTypeSpinnerAdapter
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchModalBottomSheetFragment :
    BottomSheetDialogFragment(R.layout.search_modal_bottom_sheets_fragment) {

    private val binding by viewBinding { SearchModalBottomSheetsFragmentBinding.bind(it) }
    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adapter set up
        val typeSpinnerAdapter = AddOrModifyRealEstateTypeSpinnerAdapter(
            requireContext(),
            R.layout.add_or_modify_real_estate_spinner_item
        )
        val agentSpinnerAdapter = AddOrModifyRealEstateAgentSpinnerAdapter(
            requireContext(),
            R.layout.add_or_modify_real_estate_spinner_item
        )

        val autocompleteAdapter = AutocompleteAdapter()

        binding.searchAutoCompleteTextViewAsTypeSpinner.setAdapter(typeSpinnerAdapter)
        binding.searchAutoCompleteTextViewAsAgentSpinner.setAdapter(agentSpinnerAdapter)
        binding.searchAutoCompleteTextViewCity.setAdapter(autocompleteAdapter)

        // ViewState LiveData Observer
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is SearchViewState.InitialContent -> {
                    typeSpinnerAdapter.clear()
                    typeSpinnerAdapter.addAll(viewState.typeSpinnerItemViewStateList)

                    agentSpinnerAdapter.clear()
                    agentSpinnerAdapter.addAll(viewState.agentSpinnerItemViewStateList)
                }
            }
        }

        // City DynamicLiveData
        viewModel.cityPredictionsLiveData.observe(viewLifecycleOwner) { autocompleteViewStateList ->
            autocompleteAdapter.setData(autocompleteViewStateList)
        }

        // UI elements listener //
        binding.searchAutoCompleteTextViewAsTypeSpinner.setOnItemClickListener { _, _, position, _ ->
            typeSpinnerAdapter.getItem(position)?.let { typeSpinnerItemViewState ->
                viewModel.onTypeSpinnerItemClicked(typeSpinnerItemViewState)
            }
        }

        binding.searchTextInputEditTextMinSurface.addTextChangedListener {
            viewModel.onEditTextMinSurfaceChanged(it.toString())
        }

        binding.searchTextInputEditTextMaxSurface.addTextChangedListener {
            viewModel.onEditTextMaxSurfaceChanged(it.toString())
        }

        binding.searchTextInputEditTextMinPrice.addTextChangedListener {
            viewModel.onEditTextMinPriceChanged(it.toString())
        }

        binding.searchTextInputEditTextMaxPrice.addTextChangedListener {
            viewModel.onEditTextMaxPriceChanged(it.toString())
        }

        binding.searchTextInputEditTextNumberOfBedRooms.addTextChangedListener {
            viewModel.onEditTextNumberOfBedRoomsChanged(it.toString())
        }

        binding.searchTextInputEditTextNumberOfBathRooms.addTextChangedListener {
            viewModel.onEditTextNumberOfBathRoomsChanged(it.toString())
        }

        binding.searchAutoCompleteTextViewCity.addTextChangedListener {
            viewModel.onAutocompleteCityChanged(it.toString())
        }

        binding.searchAutoCompleteTextViewAsAgentSpinner.setOnItemClickListener { _, _, position, _ ->
            agentSpinnerAdapter.getItem(position)?.let { agentSpinnerItemViewState ->
                viewModel.onAgentSpinnerItemClicked(agentSpinnerItemViewState)
            }
        }

        binding.searchChipGuard.setOnClickListener {
            viewModel.onChipGuardClicked(binding.searchChipGuard.isChecked)
        }

        binding.searchChipGarage.setOnClickListener {
            viewModel.onChipGarageClicked(binding.searchChipGarage.isChecked)
        }

        binding.searchChipGarden.setOnClickListener {
            viewModel.onChipGardenClicked(binding.searchChipGarden.isChecked)
        }

        binding.searchChipElevator.setOnClickListener {
            viewModel.onChipElevatorClicked(binding.searchChipElevator.isChecked)
        }

        binding.searchChipSoldOutRecently.setOnClickListener {
            viewModel.onChipSoldOutRecentlyClicked(binding.searchChipSoldOutRecently.isChecked)
        }

        binding.searchChipGroceryStoreNextBy.setOnClickListener {
            viewModel.onChipGroceryStoreNextByClicked(binding.searchChipGroceryStoreNextBy.isChecked)
        }

        binding.searchChipRegisteredRecently.setOnClickListener {
            viewModel.onChipRegisteredRecentlyClicked(binding.searchChipRegisteredRecently.isChecked)
        }

        binding.searchChipPhotosAvailable.setOnClickListener {
            viewModel.onChipPhotosAvailableClicked(binding.searchChipPhotosAvailable.isChecked)
        }

        binding.searchButtonApply.setOnClickListener {
            viewModel.onButtonApplyClicked() {
                dismiss()
            }
        }

        binding.searchButtonReset.setOnClickListener {
            viewModel.onButtonResetClicked() {
                dismiss()
            }
        }

    }
}
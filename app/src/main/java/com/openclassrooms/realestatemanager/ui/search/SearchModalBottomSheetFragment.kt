package com.openclassrooms.realestatemanager.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddOrModifyRealEstateFragmentBinding
import com.openclassrooms.realestatemanager.databinding.SearchModalBottomSheetsFragmentBinding
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateAgentSpinnerAdapter
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateAutocompleteAdapter
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateTypeSpinnerAdapter
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateViewModel
import com.openclassrooms.realestatemanager.utils.viewBinding

class SearchModalBottomSheetFragment : BottomSheetDialogFragment(R.layout.search_modal_bottom_sheets_fragment) {

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

        val autocompleteAdapter = AddOrModifyRealEstateAutocompleteAdapter()

        binding.searchAutoCompleteTextViewAsTypeSpinner.setAdapter(typeSpinnerAdapter)
        binding.searchAutoCompleteTextViewAsAgentSpinner.setAdapter(agentSpinnerAdapter)
        binding.searchAutoCompleteTextViewCity.setAdapter(autocompleteAdapter)

    }
}
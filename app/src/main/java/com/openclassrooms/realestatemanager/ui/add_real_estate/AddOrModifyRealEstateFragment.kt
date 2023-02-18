package com.openclassrooms.realestatemanager.ui.add_real_estate

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
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

    private val viewModel by viewModels<AddRealEstateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
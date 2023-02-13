package com.openclassrooms.realestatemanager.ui.addRealEstate

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddRealEstateFragmentBinding
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRealEstateFragment : Fragment(R.layout.add_real_estate_fragment) {

    companion object {
        fun newInstance() = AddRealEstateFragment()
    }

    private val binding by viewBinding { AddRealEstateFragmentBinding.bind(it) }

    private val viewModel by viewModels<AddRealEstateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addRealEstateTextInputEditTextAddress.addTextChangedListener() {

        }
    }
}
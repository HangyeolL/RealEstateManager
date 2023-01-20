package com.openclassrooms.realestatemanager.ui.realEstateList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstateListFragmentBinding
import com.openclassrooms.realestatemanager.utils.viewBinding

class RealEstateListFragment : Fragment(R.layout.real_estate_list_fragment){

    companion object {
        fun newInstance() = RealEstateListFragment()
    }

    private val binding by viewBinding { RealEstateListFragmentBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}
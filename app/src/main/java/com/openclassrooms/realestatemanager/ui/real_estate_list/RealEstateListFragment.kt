package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstateListFragmentBinding
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstateListFragment : Fragment(R.layout.real_estate_list_fragment) {

    // TODO How to make recyclerView Items fit properly when it is landscape mode

    companion object {
        fun newInstance() = RealEstateListFragment()
    }

    private val binding by viewBinding { RealEstateListFragmentBinding.bind(it) }

    private val viewModel by viewModels<RealEstateListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RealEstateListAdapter() {
            viewModel.onRealEstateItemClicked(it)
        }

        binding.realEstateListRecyclerView.adapter = adapter

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it.itemViewStateList)
        }



    }


}
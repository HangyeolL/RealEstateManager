package com.openclassrooms.realestatemanager.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.DetailFragmentBinding
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    companion object {
        fun newInstance() = DetailFragment()

    }

    private val binding by viewBinding { DetailFragmentBinding.bind(it) }

    private val viewModel by viewModels<DetailViewModel>()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//
//                viewModel.detailViewState.collect() { detailViewState ->
//                    detailViewState!!
//                    if (detailViewState.isViewVisible) {
//                        binding.detailConstraintLayoutParent.isVisible
//                    } else {
//                        !binding.detailConstraintLayoutParent.isVisible
//                    }
//                    binding.detailTextViewDescriptionBody.text = detailViewState.descriptionBody
//                }
//
//            }
//        }

    }
}
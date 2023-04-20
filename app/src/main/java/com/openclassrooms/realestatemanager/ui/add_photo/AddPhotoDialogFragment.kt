package com.openclassrooms.realestatemanager.ui.add_photo

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddPhotoDialogFragmentBinding
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateViewModel
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPhotoDialogFragment : DialogFragment(R.layout.add_photo_dialog_fragment) {

    private val binding by viewBinding { AddPhotoDialogFragmentBinding.bind(it) }

    // Create new viewModel from parent fragment use ownerProducer if not just delegation is fine :)
    private val viewModel by viewModels<AddOrModifyRealEstateViewModel>()

    private lateinit var navController: NavController
    private val args: AddPhotoDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_FragmentContainerView_navHost
        )

        // set dialog's width and height
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val realEstateId = args.realEstateId
        val picUriToString = args.picUriToString

        Glide.with(this).load(args.picUriToString)
            .centerCrop()
            .into(binding.addPhotoDialogImageView)

        binding.addPhotoDialogButtonCancel.setOnClickListener {
            dismiss()
        }

        binding.addPhotoDialogButtonOk.setOnClickListener {
//            viewModel.onAddPhotoButtonOkClicked(
//                realEstateId,
//                picUriToString,
//                binding.addPhotoDialogTextInputEditTextDescription.text.toString()
//            )

            navController.previousBackStackEntry?.savedStateHandle?.set(
                "picUriToString",
                picUriToString
            )
            navController.previousBackStackEntry?.savedStateHandle?.set(
                "photoDescription",
                binding.addPhotoDialogTextInputEditTextDescription.text.toString()
            )

            dismiss()
        }
    }

}
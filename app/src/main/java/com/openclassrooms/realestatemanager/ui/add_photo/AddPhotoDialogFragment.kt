package com.openclassrooms.realestatemanager.ui.add_photo

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddPhotoDialogFragmentBinding
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPhotoDialogFragment : DialogFragment(R.layout.add_photo_dialog_fragment) {

    private val binding by viewBinding { AddPhotoDialogFragmentBinding.bind(it) }

    // When viewModel is shared with parent : do ownerProducer if not just delegation wo
    private val viewModel by viewModels<AddPhotoDialogViewModel>()

    private val args: AddPhotoDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            viewModel.onButtonOkClicked(
                realEstateId,
                picUriToString,
                binding.addPhotoDialogTextInputEditTextDescription.text.toString()
            )
            dismiss()

        }
    }

}
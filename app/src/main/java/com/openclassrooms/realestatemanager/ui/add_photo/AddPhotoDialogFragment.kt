package com.openclassrooms.realestatemanager.ui.add_photo

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddPhotoDialogFragmentBinding
import com.openclassrooms.realestatemanager.utils.viewBinding

class AddPhotoDialogFragment : DialogFragment(R.layout.add_photo_dialog_fragment) {

    private val binding by viewBinding { AddPhotoDialogFragmentBinding.bind(it) }
    private val viewModel by viewModels<AddPhotoDialogViewModel>(ownerProducer = { requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var realEstateId: Int? = null
        val picUriToString = this.requireArguments().getString("PICTURE")

        if (this.requireArguments().getInt("KEY_REAL_ESTATE_ID") == 0) {
            realEstateId = null
        } else {
            realEstateId = this.requireArguments().getInt("KEY_REAL_ESTATE_ID")
        }

        Glide.with(this).load(picUriToString)
            .centerCrop()
            .into(binding.addPhotoDialogImageView)

        binding.addPhotoDialogButtonCancel.setOnClickListener {
            dismiss()
        }

        binding.addPhotoDialogButtonOk.setOnClickListener {
            picUriToString?.let { picUriToString ->
                viewModel.onButtonOkClicked(
                    realEstateId,
                    picUriToString,
                    binding.addPhotoDialogTextInputEditTextDescription.text.toString()
                )
                dismiss()
            }
        }

        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}
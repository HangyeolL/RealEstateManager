package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddPhotoDialogFragmentBinding
import com.openclassrooms.realestatemanager.utils.viewBinding

class AddPhotoDialogFragment() : DialogFragment(R.layout.add_photo_dialog_fragment) {


    private val binding by viewBinding { AddPhotoDialogFragmentBinding.bind(it) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //TODO set image and capture textView -> Insert in the database...
        Glide.with(this).load(this.requireArguments().getString("PICTURE"))
            .into(binding.addPhotoDialogImageView)


        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
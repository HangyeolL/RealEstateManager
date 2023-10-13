package com.openclassrooms.realestatemanager.ui.settings

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.SettingsFragmentBinding
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsDialogFragment : DialogFragment(R.layout.settings_fragment) {

    private val binding by viewBinding { SettingsFragmentBinding.bind(it) }

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set dialog's width and height
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        binding.settingsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.settings_radioButton_euro -> {
                    viewModel.onRadioButtonEuroClicked {
                        dismiss()
                    }
                }
                R.id.settings_radioButton_dollar -> {
                    viewModel.onRadioButtonDollarClicked {
                        dismiss()
                    }
                }
            }
        }

    }
}
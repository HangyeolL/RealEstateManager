package com.openclassrooms.realestatemanager.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.DetailFragmentBinding
import com.openclassrooms.realestatemanager.databinding.SettingsFragmentBinding
import com.openclassrooms.realestatemanager.ui.detail.DetailViewModel
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : DialogFragment(R.layout.settings_fragment) {

    private val binding by viewBinding { SettingsFragmentBinding.bind(it) }

    private val viewModel by viewModels<SettingsViewModel>()

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set dialog's width and height
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

    }
}
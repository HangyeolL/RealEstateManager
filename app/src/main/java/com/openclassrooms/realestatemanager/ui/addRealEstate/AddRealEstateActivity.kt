package com.openclassrooms.realestatemanager.ui.addRealEstate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityAddRealEstateBinding
import com.openclassrooms.realestatemanager.ui.main.MainViewModel
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRealEstateActivity : AppCompatActivity() {

    private val binding by viewBinding { ActivityAddRealEstateBinding.inflate(it) }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_real_estate_activity)
    }
}
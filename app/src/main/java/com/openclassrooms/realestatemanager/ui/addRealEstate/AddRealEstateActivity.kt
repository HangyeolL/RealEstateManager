package com.openclassrooms.realestatemanager.ui.addRealEstate

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddRealEstateActivityBinding
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRealEstateActivity : AppCompatActivity() {

    private val binding by viewBinding { AddRealEstateActivityBinding.inflate(it) }

    private val viewModel by viewModels<AddRealEstateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_real_estate_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    binding.addRealEstateFragmentContainerView.id,
                    AddRealEstateFragment.newInstance()
                )
                .commit()
        }

    }
}
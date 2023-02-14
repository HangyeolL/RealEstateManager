package com.openclassrooms.realestatemanager.ui.add_real_estate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddRealEstateActivityBinding
import com.openclassrooms.realestatemanager.utils.getIntExtraSafe
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRealEstateActivity : AppCompatActivity() {

    companion object {
        private const val KEY_REAL_ESTATE_ID = "KEY_REAL_ESTATE_ID"

        fun navigate(context: Context, realEstateId: Int?) = Intent(context, AddRealEstateActivity::class.java).apply {
            putExtra(KEY_REAL_ESTATE_ID, realEstateId)
        }
    }

    private val binding by viewBinding { AddRealEstateActivityBinding.inflate(it) }

    private val viewModel by viewModels<AddRealEstateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_real_estate_activity)

        setSupportActionBar(binding.addRealEstateToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    binding.addRealEstateFragmentContainerView.id,
                    AddRealEstateFragment.newInstance(intent.getIntExtraSafe(KEY_REAL_ESTATE_ID))
                )
                .commit()
        }
    }
}
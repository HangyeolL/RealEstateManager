package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddOrModifyRealEstateActivityBinding
import com.openclassrooms.realestatemanager.utils.getIntExtraSafe
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddOrModifyRealEstateActivity : AppCompatActivity() {

    companion object {
        private const val KEY_REAL_ESTATE_ID = "KEY_REAL_ESTATE_ID"

        fun navigate(context: Context, realEstateId: Int?) = Intent(context, AddOrModifyRealEstateActivity::class.java).apply {
            putExtra(KEY_REAL_ESTATE_ID, realEstateId)
        }
    }

    private val binding by viewBinding { AddOrModifyRealEstateActivityBinding.inflate(it) }

    private val viewModel by viewModels<AddOrModifyRealEstateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_or_modify_real_estate_activity)

        setSupportActionBar(binding.addOrModifyRealEstateToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    binding.addOrModifyRealEstateFragmentContainerView.id,
                    AddOrModifyRealEstateFragment.newInstance(intent.getIntExtraSafe(KEY_REAL_ESTATE_ID))
                )
                .commit()
        }
    }
}
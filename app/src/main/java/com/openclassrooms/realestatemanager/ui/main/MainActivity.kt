package com.openclassrooms.realestatemanager.ui.main

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.ui.DetailFragment
import com.openclassrooms.realestatemanager.ui.list.RealEstateListFragment
import com.openclassrooms.realestatemanager.utils.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding { ActivityMainBinding.inflate(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.mainFragmentContainerView.id, RealEstateListFragment.newInstance()).commit()
        }

        val containerDetailsId = binding.mainFragmentContainerViewDetail?.id
        if (containerDetailsId != null && supportFragmentManager.findFragmentById(containerDetailsId) == null) {
            supportFragmentManager.beginTransaction()
                .replace(containerDetailsId, DetailFragment())
                .commitNow()
        }




    }
}
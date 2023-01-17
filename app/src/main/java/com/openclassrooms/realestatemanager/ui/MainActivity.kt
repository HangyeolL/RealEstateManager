package com.openclassrooms.realestatemanager.ui

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.ui.list.ListFragment
import com.openclassrooms.realestatemanager.ui.search.SearchFragment
import com.openclassrooms.realestatemanager.utils.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding { ActivityMainBinding.inflate(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

//        if (savedInstanceState == null) {
//            supportFragmentManager.commitNow {
//                replace(binding.fragmentContainerView.id, ListFragment.newInstance())
//            }
//        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.id, SearchFragment.newInstance()).commit()
        }


    }
}
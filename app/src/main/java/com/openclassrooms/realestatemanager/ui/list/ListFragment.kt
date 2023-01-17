package com.openclassrooms.realestatemanager.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentListBinding
import com.openclassrooms.realestatemanager.utils.viewBinding

class ListFragment : Fragment(R.layout.fragment_list){

    companion object {
        fun newInstance() = ListFragment()
    }

    private val binding by viewBinding { FragmentListBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}
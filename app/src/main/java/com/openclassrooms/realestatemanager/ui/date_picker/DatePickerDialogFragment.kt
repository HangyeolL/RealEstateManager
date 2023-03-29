package com.openclassrooms.realestatemanager.ui.date_picker

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateViewModel
import java.time.LocalDateTime

class DatePickerDialogFragment(
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

//    // Sharing viewModel between AddOrModifyRealEstateFragment and DatePickerDialogFragment
//    private val viewModel by viewModels<AddOrModifyRealEstateViewModel>(ownerProducer = { requireParentFragment() })

    private val args: DatePickerDialogFragmentArgs by navArgs()

    private lateinit var navController: NavController

    companion object {
        private const val MARKET_SINCE = 1
        private const val DATE_OF_SOLD = 2
    }

    @SuppressLint("NewApi")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_FragmentContainerView_navHost
        )

        return when (args.fragmentNumber) {
            MARKET_SINCE -> DatePickerDialog(
                requireContext(),
                this,
                LocalDateTime.now().year,
                LocalDateTime.now().monthValue,
                LocalDateTime.now().dayOfMonth)

            DATE_OF_SOLD ->DatePickerDialog(
                requireContext(),
                this,
                LocalDateTime.now().year,
                LocalDateTime.now().monthValue,
                LocalDateTime.now().dayOfMonth)

            else -> {
                throw Exception("unknown DatePicker")
            }
        }
    }


    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        when (args.fragmentNumber) {
            MARKET_SINCE -> navController.previousBackStackEntry?.savedStateHandle?.set("MarketSince", "$day/$month/$year")
            DATE_OF_SOLD -> navController.previousBackStackEntry?.savedStateHandle?.set("DateOfSold", "$day/$month/$year")
        }

    }

}
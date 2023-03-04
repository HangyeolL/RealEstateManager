package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import java.time.LocalDateTime

class DatePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    // Sharing viewModel between AddOrModifyRealEstateFragment and DatePickerDialogFragment
    private val viewModel by viewModels<AddOrModifyRealEstateViewModel>(ownerProducer = { requireParentFragment() })

    companion object {
        private const val MARKET_SINCE = 1
        private const val DATE_OF_SOLD = 2
    }

    @SuppressLint("NewApi")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =

        when (this.requireArguments().getInt("DATE")) {
            MARKET_SINCE -> DatePickerDialog(
                requireContext(),
                this,
                LocalDateTime.now().year,
                LocalDateTime.now().monthValue,
                LocalDateTime.now().dayOfMonth
            )

            DATE_OF_SOLD -> DatePickerDialog(
                requireContext(),
                this,
                LocalDateTime.now().year,
                LocalDateTime.now().monthValue,
                LocalDateTime.now().dayOfMonth
            )

            else -> {
                //TODO how to handle else branch
                throw Exception("unknown DatePicker")
            }
        }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        when(this.requireArguments().getInt("DATE")) {
            MARKET_SINCE -> viewModel.onUserMarketSinceDateSet(year, month, day)
            DATE_OF_SOLD -> viewModel.onUserDateOfSoldSet(year, month, day)
        }
    }

}
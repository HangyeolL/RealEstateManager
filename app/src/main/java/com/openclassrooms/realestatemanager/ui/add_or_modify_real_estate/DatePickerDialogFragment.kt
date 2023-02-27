package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import java.time.LocalDateTime

class DatePickerDialogFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val viewModel by viewModels<AddOrModifyRealEstateViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return DatePickerDialog(
            requireContext(),
            this,
            LocalDateTime.now().year,
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth
        )
    }

    // TODO How to communicate with viewModel ?
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        viewModel.onUserDateSet(year, month, day)
    }

}
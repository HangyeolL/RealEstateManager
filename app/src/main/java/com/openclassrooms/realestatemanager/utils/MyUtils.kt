package com.openclassrooms.realestatemanager.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MyUtils {
    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun compareDateAndGetTheDifference(dateInString: String?) : Int {
            return if (dateInString != null) {
                val formattedDate = LocalDate.parse(dateInString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                if (formattedDate.isBefore(LocalDate.now())) {
                    Log.d("HL", "${ChronoUnit.DAYS.between(formattedDate, LocalDate.now()).toInt()}")
                    ChronoUnit.DAYS.between(formattedDate, LocalDate.now()).toInt()
                } else {
                    91
                }
            } else {
                91
            }
        }

    }
}
package com.openclassrooms.realestatemanager.utils

import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.internal.format
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
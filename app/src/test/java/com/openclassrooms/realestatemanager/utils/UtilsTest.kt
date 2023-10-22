package com.openclassrooms.realestatemanager.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.text.Typography.dollar

class UtilsTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `Convert 10 dollar to 8 euro`() {
        //Given
        val givenDollar = 10
        val expectedEuroValue = 8

        //Then
        assertEquals(expectedEuroValue, Utils.convertDollarToEuro(givenDollar))
    }

    @Test
    fun `Convert 10 euro to 12 dollar`() {
        // Given
        val givenEuro = 10
        val expectedDollarValue = 12

        // Then
        assertEquals(expectedDollarValue, Utils.convertEuroToDollar(givenEuro))
    }

    @Test
    fun return_date_to_ddMMyyyy_format() {
        val dateOfDay = LocalDate.now()
        val rightFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        val wrongFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val rightFormattedDate = dateOfDay.format(rightFormatter)
        val wrongFormattedDate = dateOfDay.format(wrongFormatter)
        val dateToCheck = Utils.getTodayDateInRightFormat()

        assertThat(dateToCheck).isEqualTo(rightFormattedDate)
        assertThat(dateToCheck).isNotEqualTo(wrongFormattedDate)
    }


}
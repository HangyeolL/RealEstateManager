package com.openclassrooms.realestatemanager.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
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
    fun `Change date format to days first from years first`() {
        // Given
        val givenDateFormat = "1999.12.25"
        val expectedDateFormat = "25/12/1999"

        // Then
        assertEquals(expectedDateFormat, Utils.changeDateFormatToDaysFirstFromYearsFirst(givenDateFormat))
    }


}
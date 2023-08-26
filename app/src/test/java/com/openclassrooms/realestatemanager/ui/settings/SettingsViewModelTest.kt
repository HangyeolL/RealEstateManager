package com.openclassrooms.realestatemanager.ui.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openclassrooms.realestatemanager.TestCoroutineRule
import com.openclassrooms.realestatemanager.domain.datastore.DataStoreRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val dataStoreRepository: DataStoreRepository = mockk()

    private lateinit var settingsViewModel: SettingsViewModel

    @Before
    fun setUp() {

        coJustRun { dataStoreRepository.writeDollarBooleanToTrue() }
        coJustRun { dataStoreRepository.writeDollarBooleanToFalse() }

        settingsViewModel = SettingsViewModel(
            coroutineDispatcherProvider = testCoroutineRule.getTestCoroutineDispatcherProvider(),
            dataStoreRepository = dataStoreRepository
        )
    }

    @Test
    fun `on radio button dollar clicked`() =
        testCoroutineRule.runTest {
            // Given
            settingsViewModel.onRadioButtonDollarClicked { }

            delay(100)

            // Then
            coVerify {
                dataStoreRepository.writeDollarBooleanToTrue()
            }
        }


    @Test
    fun `on radio button euro clicked`() =
        testCoroutineRule.runTest {
            // Given
            settingsViewModel.onRadioButtonEuroClicked { }

            delay(100)

            // Then
            coVerify {
                dataStoreRepository.writeDollarBooleanToFalse()
            }
        }



}



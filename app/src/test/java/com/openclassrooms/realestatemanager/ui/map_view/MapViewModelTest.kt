package com.openclassrooms.realestatemanager.ui.map_view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openclassrooms.realestatemanager.TestCoroutineRule
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import com.openclassrooms.realestatemanager.domain.realestate.CurrentRealEstateIdRepository
import com.openclassrooms.realestatemanager.domain.realestate.RealEstateRepository
import com.openclassrooms.realestatemanager.getDefaultRealEstateEntity
import com.openclassrooms.realestatemanager.getDefaultRealEstateEntityList
import com.openclassrooms.realestatemanager.observeForTesting
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MapViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    companion object {
        private const val DEFAULT_LATITUDE = 48.85648195552058
        private const val DEFAULT_LONGITUDE = 2.352610864067402
    }

    private val currentRealEstateIdRepository: CurrentRealEstateIdRepository = mockk()
    private val realEstateRepository: RealEstateRepository = mockk()
    private val locationRepository: LocationRepository = mockk()

    private lateinit var mapViewModel: MapViewModel

    @Before
    fun setUp() {

        every { realEstateRepository.getAllRealEstates() } returns flowOf(getDefaultRealEstateEntityList(5))
        every { locationRepository.getLocationStateFlow() } returns MutableStateFlow(null)
        justRun { currentRealEstateIdRepository.setCurrentRealEstateId(any()) }

        mapViewModel = MapViewModel(
            coroutineDispatcherProvider = testCoroutineRule.getTestCoroutineDispatcherProvider(),
            currentRealEstateRepository = currentRealEstateIdRepository,
            realEstateRepository = realEstateRepository,
            locationRepository = locationRepository
        )
    }

    @Test
    fun `nominal case`() = testCoroutineRule.runTest {
        // Given
        val expectedViewState = getDefaultMapViewState()

        // When
        mapViewModel.viewStateLiveData.observeForTesting(this) {
            // Then
            Assert.assertEquals(expectedViewState, it.value)
        }
    }

    @Test
    fun `repository method should be called on marker info window clicked`() =
        testCoroutineRule.runTest {
            // Given When
            mapViewModel.onMarkerInfoWindowClicked(1) {}

            // Then
            verify {
                currentRealEstateIdRepository.setCurrentRealEstateId(1)
            }
        }

    // Region OUT

    private fun getDefaultMapViewState() =
        MapViewState(
            mapMarkerViewStateList = getDefaultMapMarkerViewStateList(5),
        )

    private fun getDefaultMapMarkerViewState(realEstateId: Int) =
        MapMarkerViewState(
            realEstateAddress = getDefaultRealEstateEntity(realEstateId).address,
            realEstateLatLng = getDefaultRealEstateEntity(realEstateId).latLng,
            selectedRealEstateId = getDefaultRealEstateEntity(realEstateId).realEstateId
        )

    private fun getDefaultMapMarkerViewStateList(sizeCount: Int) =
        List(sizeCount) { index ->
            getDefaultMapMarkerViewState(index)
        }


}
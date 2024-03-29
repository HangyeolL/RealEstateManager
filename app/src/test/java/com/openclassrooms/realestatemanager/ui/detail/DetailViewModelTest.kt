package com.openclassrooms.realestatemanager.ui.detail

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openclassrooms.realestatemanager.*
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.realestate.CurrentRealEstateIdRepository
import com.openclassrooms.realestatemanager.domain.realestate.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.detail_map.DetailMapViewState
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val context: Application = mockk()
    private val currentRealEstateIdRepository: CurrentRealEstateIdRepository = mockk()
    private val realEstateRepository: RealEstateRepository = mockk()
    private val agentRepository: AgentRepository = mockk()

    private var currentRealEstateIdMutableStateFlow = MutableStateFlow(1)

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        every { currentRealEstateIdRepository.getCurrentRealEstateId() } returns currentRealEstateIdMutableStateFlow
        every { realEstateRepository.getRealEstateWithPhotosById(any()) } returns
                flowOf(getDefaultRealEstateWithPhotos(currentRealEstateIdMutableStateFlow.value))
        every { agentRepository.getAllAgents() } returns flowOf(getDefaultAgentList())

        detailViewModel = DetailViewModel(
            coroutineDispatcherProvider = testCoroutineRule.getTestCoroutineDispatcherProvider(),
            application = context,
            currentRealEstateIdRepository = currentRealEstateIdRepository,
            realEstateRepository = realEstateRepository,
            agentRepository = agentRepository,
        )
    }

    @Test
    fun `nominal case - detailViewState`() = testCoroutineRule.runTest {
        // Given
        val expectedViewState = getDefaultDetailViewState(currentRealEstateIdMutableStateFlow.value)

        // When
        detailViewModel.detailViewStateLiveData.observeForTesting(this) {

            // Then
            Assert.assertEquals(expectedViewState, it.value)
        }
    }

    @Test
    fun `edge case - currentRealEstateId is 2`() = testCoroutineRule.runTest {
        // Given
        val currentRealEstateIdSpy = spyk(currentRealEstateIdMutableStateFlow)
        currentRealEstateIdSpy.value = 2

        val expectedViewState = getDefaultDetailViewState(currentRealEstateIdMutableStateFlow.value)

        // When
        detailViewModel.detailViewStateLiveData.observeForTesting(this) {

            // Then
            Assert.assertEquals(expectedViewState, it.value)
        }
    }

    @Test
    fun `nominal case - detailMapViewState`() = testCoroutineRule.runTest {
        // Given
        val expectedViewState =
            getDefaultDetailMapViewState(currentRealEstateIdMutableStateFlow.value)

        // When
        detailViewModel.mapViewStateLiveData.observeForTesting(this) {

            // Then
            Assert.assertEquals(expectedViewState, it.value)
        }
    }

    // Region OUT //

    private fun getDefaultDetailViewState(currentRealEstateId: Int) = DetailViewState(
        getDefaultRealEstatePhotoItemViewStateList(currentRealEstateId),
        descriptionBody = getDefaultRealEstateEntity(currentRealEstateId).descriptionBody,
        squareMeter = getDefaultRealEstateEntity(currentRealEstateId).squareMeter,
        numberOfRooms = getDefaultRealEstateEntity(currentRealEstateId).numberOfRooms,
        numberOfBedrooms = getDefaultRealEstateEntity(currentRealEstateId).numberOfBedrooms,
        numberOfBathrooms = getDefaultRealEstateEntity(currentRealEstateId).numberOfBathrooms,
        address = getDefaultRealEstateEntity(currentRealEstateId).address,
        agentName =
        getDefaultAgentEntity(
            getDefaultRealEstateEntity(currentRealEstateId).agentIdInCharge
        ).name,
        agentPhotoUrl = getDefaultAgentEntity(
            getDefaultRealEstateEntity(currentRealEstateId).agentIdInCharge
        ).photoUrl,
        isViewVisible = true
    )

    private fun getDefaultDetailMapViewState(currentRealEstateId: Int) = DetailMapViewState(
        getDefaultRealEstateEntity(currentRealEstateId).latLng
    )

}
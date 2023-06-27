package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openclassrooms.realestatemanager.*
import com.openclassrooms.realestatemanager.domain.datastore.DataStoreRepository
import com.openclassrooms.realestatemanager.domain.realestate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realestate.RealEstateRepository
import com.openclassrooms.realestatemanager.domain.search_criteria.SearchCriteriaRepository
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RealEstateListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val context: Application = mockk()
    private val realEstateRepository: RealEstateRepository = mockk()
    private val currentRealEstateRepository: CurrentRealEstateRepository = mockk()
    private val searchCriteriaRepository: SearchCriteriaRepository = mockk()
    private val dataStoreRepository: DataStoreRepository = mockk()

    private val searchCriteriaMutableStateFlow = MutableStateFlow(null)
    private val dollarBooleanFlow = flowOf<Boolean>(false)
    private val currentRealEstateIdMutableStateFlow = MutableStateFlow(1)

    private lateinit var realEstateListViewModel: RealEstateListViewModel

    @Before
    fun setUp() {
        every { context.getString(R.string.euro_symbol_as_string) } returns "Euro symbol"
        every { realEstateRepository.getRealEstatesWithPhotos() } returns flowOf(
            getDefaultRealEstatesWithPhotosList()
        )
        every { searchCriteriaRepository.getSearchCriteria() } returns searchCriteriaMutableStateFlow
        every { dataStoreRepository.readDollarBoolean() } returns dollarBooleanFlow

        every { currentRealEstateRepository.getCurrentRealEstateId() } returns currentRealEstateIdMutableStateFlow
        every { currentRealEstateRepository.getCurrentRealEstateId().value } returns currentRealEstateIdMutableStateFlow.value

        coJustRun { currentRealEstateRepository.getCurrentRealEstateId().collect {} }

        realEstateListViewModel = RealEstateListViewModel(
            coroutineDispatcherProvider = testCoroutineRule.getTestCoroutineDispatcherProvider(),
            context = context,
            realEstateRepository = realEstateRepository,
            currentRealEstateRepository = currentRealEstateRepository,
            searchCriteriaRepository = searchCriteriaRepository,
            dataStoreRepository = dataStoreRepository
        )
    }

    @Test
    fun `nominal case`() = testCoroutineRule.runTest {
        // Given
        val expectedViewState = getDefaultViewState()

        // When
        realEstateListViewModel.viewStateLiveData.observeForTesting(this) {

            // Then
            assertEquals(expectedViewState, it.value)
        }
    }

    // Region OUT //

    private fun getDefaultViewState() = RealEstateListViewState(
        getDefaultItemViewStateList()
    )

    private fun getDefaultItemViewStateList() = listOf(
        getDefaultItemViewState(1, 1),
        getDefaultItemViewState(2, 1),
        getDefaultItemViewState(3, 2),
        getDefaultItemViewState(4, 3),
        getDefaultItemViewState(5, 3),
    )

    private fun getDefaultItemViewState(realEstateId: Int, photoId: Int) =
        RealEstateListItemViewState(
            id = getDefaultRealEstateEntity(realEstateId).realEstateId,
            imageUrl = getDefaultRealEstatePhoto(photoId, realEstateId).url,
            type = getDefaultRealEstateEntity(realEstateId).type,
            city = getDefaultRealEstateEntity(realEstateId).city,
            price = getDefaultRealEstateEntity(realEstateId).price,
            currencySymbol = "Euro symbol",
            isSoldOut = getDefaultRealEstateEntity(realEstateId).isSoldOut,
        )
}
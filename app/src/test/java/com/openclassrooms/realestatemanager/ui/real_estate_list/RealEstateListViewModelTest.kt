package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openclassrooms.realestatemanager.*
import com.openclassrooms.realestatemanager.domain.datastore.DataStoreRepository
import com.openclassrooms.realestatemanager.domain.realestate.CurrentRealEstateIdRepository
import com.openclassrooms.realestatemanager.domain.realestate.RealEstateRepository
import com.openclassrooms.realestatemanager.domain.search_criteria.SearchCriteriaRepository
import io.mockk.*
import kotlinx.coroutines.flow.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

class RealEstateListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val context: Application = mockk()
    private val realEstateRepository: RealEstateRepository = mockk()
    private val currentRealEstateIdRepository: CurrentRealEstateIdRepository = mockk()
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
            getDefaultRealEstateListWithPhotos()
        )
        every { searchCriteriaRepository.getSearchCriteria() } returns searchCriteriaMutableStateFlow
        every { dataStoreRepository.readDollarBoolean() } returns dollarBooleanFlow

        every { currentRealEstateIdRepository.getCurrentRealEstateId() } returns currentRealEstateIdMutableStateFlow
        every { currentRealEstateIdRepository.getCurrentRealEstateId().value } returns currentRealEstateIdMutableStateFlow.value

        coJustRun {
            currentRealEstateIdRepository.getCurrentRealEstateId().collectLatest {}
        }

        realEstateListViewModel = RealEstateListViewModel(
            coroutineDispatcherProvider = testCoroutineRule.getTestCoroutineDispatcherProvider(),
            context = context,
            realEstateRepository = realEstateRepository,
            currentRealEstateIdRepository = currentRealEstateIdRepository,
            searchCriteriaRepository = searchCriteriaRepository,
            dataStoreRepository = dataStoreRepository
        )
    }

//    @Test
//    fun `nominal case`() = testCoroutineRule.runTest {
//        // Given
//        val expectedViewState = getDefaultViewState()
//
//        // When
//        realEstateListViewModel.viewStateLiveData.observeForTesting(this) {
//
//            // Then
//            assertEquals(expectedViewState, it.value)
//        }
//    }

    // Region OUT //

    private fun getDefaultViewState() = RealEstateListViewState(
        getDefaultItemViewStateList()
    )

    private fun getDefaultItemViewStateList() = listOf(
        getDefaultItemViewState(0),
        getDefaultItemViewState(1),
        getDefaultItemViewState(2),
        getDefaultItemViewState(3),
        getDefaultItemViewState(4),
    )

    private fun getDefaultItemViewState(realEstateId: Int) =
        RealEstateListItemViewState(
            id = getDefaultRealEstateEntity(realEstateId).realEstateId,
            imageUrl = getDefaultRealEstatePhotoEntity(realEstateId).url,
            type = getDefaultRealEstateEntity(realEstateId).type,
            city = getDefaultRealEstateEntity(realEstateId).city,
            price = getDefaultRealEstateEntity(realEstateId).price,
            currencySymbol = "Euro symbol",
            isSoldOut = getDefaultRealEstateEntity(realEstateId).isSoldOut,
        )
}
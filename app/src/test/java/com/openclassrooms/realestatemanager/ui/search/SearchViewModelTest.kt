package com.openclassrooms.realestatemanager.ui.search

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openclassrooms.realestatemanager.*
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.model.AutocompleteEntity
import com.openclassrooms.realestatemanager.domain.search_criteria.SearchCriteriaRepository
import io.mockk.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val context: Application = mockk()
    private val agentRepository: AgentRepository = mockk()
    private val autocompleteRepository: AutocompleteRepository = mockk()
    private val searchCriteriaRepository: SearchCriteriaRepository = mockk()

    private lateinit var searchViewModel: SearchViewModel

    private var autocompleteEntityListMutableSharedFlow: MutableSharedFlow<List<AutocompleteEntity>> =
        MutableSharedFlow(replay = 1)

    @Before
    fun setUp() {
        autocompleteEntityListMutableSharedFlow.tryEmit(getDefaultAutocompleteEntityList())

        every { context.getString(R.string.house) } returns "House"
        every { context.getString(R.string.apartment) } returns "Apartment"
        every { context.getString(R.string.studio) } returns "Studio"

        every { agentRepository.getAllAgents() } returns flowOf(getDefaultAgentList())
        every { autocompleteRepository.getAutocompleteEntitiesForCity() } returns
                autocompleteEntityListMutableSharedFlow

        coJustRun { autocompleteRepository.requestMyAutocompleteResponseOfCity(any()) }
        justRun { searchCriteriaRepository.resetSearchCriteria() }
        justRun { searchCriteriaRepository.setSearchCriteria(any()) }

        searchViewModel = SearchViewModel(
            coroutineDispatcherProvider = testCoroutineRule.getTestCoroutineDispatcherProvider(),
            application = context,
            agentRepository = agentRepository,
            autocompleteRepository = autocompleteRepository,
            searchCriteriaRepository = searchCriteriaRepository
        )
    }

    @Test
    fun `nominal case - search view state live data`() = testCoroutineRule.runTest {
        // Given
        val expectedViewState = getDefaultSearchViewState()

        // When
        searchViewModel.viewStateLiveData.observeForTesting(this) {

            // Then
            Assert.assertEquals(expectedViewState, it.value)
        }
    }

    @Test
    fun `nominal case - city predictions live data`() = testCoroutineRule.runTest {
        // Given
        val expectedViewState = getDefaultAutocompleteTextViewStateList()

        // When
        searchViewModel.cityPredictionsLiveData.observeForTesting(this) {

            // Then
            Assert.assertEquals(expectedViewState, it.value)
        }
    }

    @Test
    fun `check if the autocompleteRepository method is called on autocomplete text view city changed`() =
        testCoroutineRule.runTest {
            // Given
            searchViewModel.onAutocompleteCityChanged("userInput")

            delay(100)

            coVerify {
                autocompleteRepository.requestMyAutocompleteResponseOfCity(any())
            }

        }

    @Test
    fun `check if the searchCriteriaRepository method is called on button apply clicked`() =
        testCoroutineRule.runTest {
            // Given
            searchViewModel.onButtonApplyClicked {}

            coVerify {
                searchCriteriaRepository.setSearchCriteria(any())
            }

        }

    @Test
    fun `check if the searchCriteriaRepository method is called on button reset clicked`() =
        testCoroutineRule.runTest {
            // Given
            searchViewModel.onButtonResetClicked {}

            coVerify {
                searchCriteriaRepository.resetSearchCriteria()
            }

        }

    // Region OUT //

    private fun getDefaultSearchViewState() = SearchViewState.InitialContent(
        getDefaultRealEstateTypeSpinnerItemViewStateList(),
        getDefaultAgentSpinnerItemViewStateList()
    )


}
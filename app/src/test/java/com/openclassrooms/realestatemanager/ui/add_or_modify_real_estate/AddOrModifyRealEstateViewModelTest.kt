package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.Application
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.openclassrooms.realestatemanager.*
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.model.AutocompleteEntity
import com.openclassrooms.realestatemanager.domain.geocoding.GeocodingRepository
import com.openclassrooms.realestatemanager.domain.realestate.RealEstateRepository
import io.mockk.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddOrModifyRealEstateViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    companion object {
        private const val REAL_ESTATE_ID_KEY = "realEstateId"
        private const val SELECTED_REAL_ESTATE_ID = 1
        private const val DEFAULT_REAL_ESTATE_ID = 0
        private const val PHOTO_ID = 1

    }

    private val context: Application = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val agentRepository: AgentRepository = mockk()
    private val realEstateRepository: RealEstateRepository = mockk()
    private val autocompleteRepository: AutocompleteRepository = mockk()
    private val geocodingRepository: GeocodingRepository = mockk()

    private lateinit var addOrModifyRealEstateViewModel: AddOrModifyRealEstateViewModel

    private var realEstateIdValue: Int = SELECTED_REAL_ESTATE_ID

    private var autocompleteEntityListMutableSharedFlow: MutableSharedFlow<List<AutocompleteEntity>> =
        MutableSharedFlow(replay = 1)

    @Before
    fun setUp() {
        every { context.getString(R.string.house) } returns "House"
        every { context.getString(R.string.apartment) } returns "Apartment"
        every { context.getString(R.string.studio) } returns "Studio"

        // Use the setup defined in the previous answer to handle dynamic return values
        every { savedStateHandle.get<Int>(REAL_ESTATE_ID_KEY) } answers { realEstateIdValue }

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0

        every { realEstateRepository.getRealEstateWithPhotosById(SELECTED_REAL_ESTATE_ID) } returns
                flowOf(getDefaultRealEstateWithPhotos(SELECTED_REAL_ESTATE_ID))
        every { realEstateRepository.getRealEstateWithPhotosById(DEFAULT_REAL_ESTATE_ID) } returns
                flowOf(getDefaultRealEstateWithPhotos(DEFAULT_REAL_ESTATE_ID))
        every { agentRepository.getAllAgents() } returns flowOf(getDefaultAgentList())

        autocompleteEntityListMutableSharedFlow.tryEmit(getDefaultAutocompleteEntityList())
        every { autocompleteRepository.getAutocompleteEntitiesForCity() } returns
                autocompleteEntityListMutableSharedFlow
        every { autocompleteRepository.getAutocompleteEntitiesForAddress() } returns
                autocompleteEntityListMutableSharedFlow

        coJustRun { autocompleteRepository.requestMyAutocompleteResponseOfCity(any()) }
        coJustRun { autocompleteRepository.requestMyAutocompleteResponseOfAddress(any()) }
        coEvery { geocodingRepository.requestMyGeocodingResponse(any()) } returns getDefaultGeocodingEntity()

        coEvery { realEstateRepository.insertRealEstate(any()) {} } returns SELECTED_REAL_ESTATE_ID.toLong()
        coJustRun { realEstateRepository.updateRealEstate(any()) }
        coJustRun { realEstateRepository.insertRealEstatePhoto(any()) }

        addOrModifyRealEstateViewModel = AddOrModifyRealEstateViewModel(
            coroutineDispatcherProvider = testCoroutineRule.getTestCoroutineDispatcherProvider(),
            application = context,
            savedStateHandle = savedStateHandle,
            agentRepository = agentRepository,
            realEstateRepository = realEstateRepository,
            autoCompleteRepository = autocompleteRepository,
            geocodingRepository = geocodingRepository
        )
    }

    @Test
    fun `modifying an existing entity case - selected real estate Id`() =
        testCoroutineRule.runTest {
            // Given
            val expectedViewState =
                getDefaultAddOrModifyRealEstateViewState(SELECTED_REAL_ESTATE_ID)

            // When
            addOrModifyRealEstateViewModel.initialViewStateLiveData.observeForTesting(this) {
                // Then
                Assert.assertEquals(expectedViewState, it.value)
            }
        }

    @Test
    fun `creating a new entity case - default real estate id`() =
        testCoroutineRule.runTest {
            // Given
            realEstateIdValue = DEFAULT_REAL_ESTATE_ID

            addOrModifyRealEstateViewModel = AddOrModifyRealEstateViewModel(
                coroutineDispatcherProvider = testCoroutineRule.getTestCoroutineDispatcherProvider(),
                application = context,
                savedStateHandle = savedStateHandle,
                agentRepository = agentRepository,
                realEstateRepository = realEstateRepository,
                autoCompleteRepository = autocompleteRepository,
                geocodingRepository = geocodingRepository
            )

            val expectedViewState =
                getDefaultAddOrModifyRealEstateViewState(DEFAULT_REAL_ESTATE_ID)

            // When
            addOrModifyRealEstateViewModel.initialViewStateLiveData.observeForTesting(this) {
                // Then
                Assert.assertEquals(expectedViewState, it.value)
            }
        }

    @Test
    fun `nominal case - city predictions live data`() = testCoroutineRule.runTest {
        // Given
        val expectedViewState = getDefaultAutocompleteTextViewStateList()

        // When
        addOrModifyRealEstateViewModel.cityPredictionsLiveData.observeForTesting(this) {

            // Then
            Assert.assertEquals(expectedViewState, it.value)
        }
    }

    @Test
    fun `nominal case - address predictions live data`() = testCoroutineRule.runTest {
        // Given
        val expectedViewState = getDefaultAutocompleteTextViewStateList()

        // When
        addOrModifyRealEstateViewModel.addressPredictionsLiveData.observeForTesting(this) {

            // Then
            Assert.assertEquals(expectedViewState, it.value)
        }
    }

    @Test
    fun `check if the repository methods are called on autocomplete address changed`() =
        testCoroutineRule.runTest {
            // Given
            addOrModifyRealEstateViewModel.onAutocompleteAddressChanged("userInput")

            delay(100)

            coVerify {
                autocompleteRepository.requestMyAutocompleteResponseOfAddress(any())
                geocodingRepository.requestMyGeocodingResponse(any())
            }

        }

    @Test
    fun `check if the repository methods are called on on edit text city changed`() =
        testCoroutineRule.runTest {
            // Given
            addOrModifyRealEstateViewModel.onEditTextCityChanged("userInput")

            delay(100)

            coVerify {
                autocompleteRepository.requestMyAutocompleteResponseOfCity(any())
            }

        }

//    @Test
//    fun `modifying an existing entity case - on save button clicked with selected real estate id`() =
//        testCoroutineRule.runTest {
//            // Given
//            addOrModifyRealEstateViewModel.onTypeSpinnerItemClicked(
//                getDefaultRealEstateTypeSpinnerItemViewState(
//                    icon = R.drawable.ic_baseline_bed_24,
//                    type = getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).type
//                )
//            )
//            addOrModifyRealEstateViewModel.onAutocompleteAddressItemClicked(
//                getDefaultAutocompleteTextViewStateOfAddress(SELECTED_REAL_ESTATE_ID)
//            )
//            addOrModifyRealEstateViewModel.onAutocompleteCityItemClicked(
//                getDefaultAutocompleteTextViewStateOfCity(SELECTED_REAL_ESTATE_ID)
//            )
//            addOrModifyRealEstateViewModel.onEditTextNumberOfRoomsChanged(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).numberOfRooms.toString()
//            )
//            addOrModifyRealEstateViewModel.onEditTextNumberOfBathRoomsChanged(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).numberOfBathrooms.toString()
//            )
//            addOrModifyRealEstateViewModel.onEditTextNumberOfBedRoomsChanged(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).numberOfBedrooms.toString()
//            )
//            addOrModifyRealEstateViewModel.onEditTextSqmChanged(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).squareMeter.toString()
//            )
//            addOrModifyRealEstateViewModel.onEditTextPriceChanged(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).price.toString()
//            )
//            addOrModifyRealEstateViewModel.onDefaultMarketSinceValueSet(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).marketSince
//            )
//            addOrModifyRealEstateViewModel.onUserMarketSinceDateSet(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).marketSince
//            )
//            addOrModifyRealEstateViewModel.onChipGuardClicked(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).guard
//            )
//            addOrModifyRealEstateViewModel.onChipGarageClicked(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).garage
//            )
//            addOrModifyRealEstateViewModel.onChipGardenClicked(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).garden
//            )
//            addOrModifyRealEstateViewModel.onChipElevatorClicked(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).elevator
//            )
//            addOrModifyRealEstateViewModel.onChipGroceryStoreNextByClicked(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).groceryStoreNearby
//            )
//            addOrModifyRealEstateViewModel.onChipIsSoldOutClicked(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).isSoldOut
//            )
//            addOrModifyRealEstateViewModel.onEditTextDescriptionChanged(
//                getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).descriptionBody
//            )
//            addOrModifyRealEstateViewModel.onAgentSpinnerItemClicked(
//                getDefaultAgentSpinnerItemViewState(
//                    getDefaultRealEstateEntity(SELECTED_REAL_ESTATE_ID).agentIdInCharge
//                )
//            )
//            addOrModifyRealEstateViewModel.onPicUriToStringSet(
//                getDefaultRealEstatePhotoEntity(PHOTO_ID, SELECTED_REAL_ESTATE_ID).url
//            )
//            addOrModifyRealEstateViewModel.photoDescriptionSet(
//                getDefaultRealEstatePhotoEntity(
//                    PHOTO_ID,
//                    SELECTED_REAL_ESTATE_ID
//                ).description.toString()
//            )
//
//            // When
//            addOrModifyRealEstateViewModel.onSaveButtonClicked { }
//
//            this.advanceTimeBy(1000)
//
//            coVerify {
//                realEstateRepository.updateRealEstate(
//                    getDefaultRealEstateEntity(
//                        SELECTED_REAL_ESTATE_ID
//                    )
//                )
//                realEstateRepository.insertRealEstatePhoto(
//                    getDefaultRealEstatePhotoEntity(
//                        PHOTO_ID,
//                        SELECTED_REAL_ESTATE_ID
//                    )
//                )
//            }
//
//        }


    // Region OUT //
    private fun getDefaultAddOrModifyRealEstateViewState(realEstateId: Int) =
        if (realEstateId == SELECTED_REAL_ESTATE_ID) {
            AddOrModifyRealEstateViewState.Modification(
                typeSpinnerItemViewStateList = getDefaultRealEstateTypeSpinnerItemViewStateList(),
                agentSpinnerItemViewStateList = getDefaultAgentSpinnerItemViewStateList(),
                realEstatePhotoListItemViewStateList = getDefaultRealEstatePhotoItemViewStateList(realEstateId)
                    .plus(getDefaultRealEstatePhotoItemViewStateListAsAddPhoto()),
                address = getDefaultRealEstateEntity(realEstateId).address,
                city = getDefaultRealEstateEntity(realEstateId).city,
                numberOfRooms = getDefaultRealEstateEntity(realEstateId).numberOfRooms.toString(),
                numberOfBathrooms = getDefaultRealEstateEntity(realEstateId).numberOfBathrooms.toString(),
                numberOfBedrooms = getDefaultRealEstateEntity(realEstateId).numberOfBedrooms.toString(),
                squareMeter = getDefaultRealEstateEntity(realEstateId).squareMeter.toString(),
                marketSince = getDefaultRealEstateEntity(realEstateId).marketSince,
                price = getDefaultRealEstateEntity(realEstateId).price.toString(),
                garage = getDefaultRealEstateEntity(realEstateId).garage,
                guard = getDefaultRealEstateEntity(realEstateId).guard,
                garden = getDefaultRealEstateEntity(realEstateId).garden,
                elevator = getDefaultRealEstateEntity(realEstateId).elevator,
                groceryStoreNearby = getDefaultRealEstateEntity(realEstateId).groceryStoreNearby,
                isSoldOut = getDefaultRealEstateEntity(realEstateId).isSoldOut,
                dateOfSold = getDefaultRealEstateEntity(realEstateId).dateOfSold,
                description = getDefaultRealEstateEntity(realEstateId).descriptionBody
            )
        } else {
            AddOrModifyRealEstateViewState.Creation(
                typeSpinnerItemViewStateList = getDefaultRealEstateTypeSpinnerItemViewStateList(),
                agentSpinnerItemViewStateList = getDefaultAgentSpinnerItemViewStateList(),
                realEstatePhotoListItemViewStateList = getDefaultRealEstatePhotoItemViewStateListAsAddPhoto(),
            )
        }


}
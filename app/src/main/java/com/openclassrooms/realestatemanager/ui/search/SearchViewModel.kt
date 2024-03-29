package com.openclassrooms.realestatemanager.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.design_system.autocomplete_text_view.AutocompleteTextViewState
import com.openclassrooms.realestatemanager.design_system.real_estate_agent.RealEstateAgentSpinnerItemViewState
import com.openclassrooms.realestatemanager.design_system.real_estate_type.RealEstateTypeSpinnerItemViewState
import com.openclassrooms.realestatemanager.domain.CoroutineDispatcherProvider
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.domain.search_criteria.SearchCriteriaRepository
import com.openclassrooms.realestatemanager.domain.search_criteria.model.SearchCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    application: Application,
    agentRepository: AgentRepository,
    private val autocompleteRepository: AutocompleteRepository,
    private val searchCriteriaRepository: SearchCriteriaRepository,
) : ViewModel() {

    val viewStateLiveData: LiveData<SearchViewState> = liveData(coroutineDispatcherProvider.io) {
        coroutineScope {
            val typeSpinnerItemViewStateList = listOf(
                RealEstateTypeSpinnerItemViewState(
                    R.drawable.ic_baseline_house_24,
                    application.getString(R.string.house)
                ),
                RealEstateTypeSpinnerItemViewState(
                    R.drawable.ic_baseline_apartment_24,
                    application.getString(R.string.apartment)
                ),
                RealEstateTypeSpinnerItemViewState(
                    R.drawable.ic_baseline_bed_24,
                    application.getString(R.string.studio)
                )
            )

            val agentSpinnerItemViewStateList = withContext(coroutineDispatcherProvider.io) {
                    agentRepository.getAllAgents().first()
                }.map { agentEntity ->
                RealEstateAgentSpinnerItemViewState(
                    agentIdInCharge = agentEntity.agentId,
                    agentNameInCharge = agentEntity.name,
                    agentPhoto = agentEntity.photoUrl
                )
            }

            emit(
                SearchViewState.InitialContent(
                    typeSpinnerItemViewStateList = typeSpinnerItemViewStateList,
                    agentSpinnerItemViewStateList = agentSpinnerItemViewStateList,
                )
            )
        }
    }

    val cityPredictionsLiveData: LiveData<List<AutocompleteTextViewState>> =
        liveData(coroutineDispatcherProvider.io) {
            autocompleteRepository.getAutocompleteEntitiesForCity()
                .collect { autocompleteEntities ->
                    emit(
                        autocompleteEntities.map { autocompleteEntity ->
                            AutocompleteTextViewState(
                                text = autocompleteEntity.text
                            )
                        }
                    )
                }
        }

    private var type: String? = null
    private var minSurface: Int? = null
    private var maxSurface: Int? = null
    private var minPrice: Int? = null
    private var maxPrice: Int? = null
    private var numberOfBedRooms: Int? = null
    private var numberOfBathRooms: Int? = null
    private var city: String? = null
    private var garage: Boolean? = null
    private var guard: Boolean? = null
    private var garden: Boolean? = null
    private var elevator: Boolean? = null
    private var groceryStoreNearby: Boolean? = null
    private var soldOutRecently: Boolean? = null
    private var registeredRecently: Boolean? = null
    private var photoAvailable: Boolean? = null
    private var agentIdInCharge: Int? = null

    fun onTypeSpinnerItemClicked(selectedItem: RealEstateTypeSpinnerItemViewState) {
        type = selectedItem.type
    }

    fun onEditTextMinSurfaceChanged(userInput: String) {
        minSurface = userInput.toIntOrNull()
    }

    fun onEditTextMaxSurfaceChanged(userInput: String) {
        maxSurface = userInput.toIntOrNull()
    }

    fun onEditTextMinPriceChanged(userInput: String) {
        minPrice = userInput.toIntOrNull()
    }

    fun onEditTextMaxPriceChanged(userInput: String) {
        maxPrice = userInput.toIntOrNull()
    }

    fun onEditTextNumberOfBedRoomsChanged(userInput: String) {
        numberOfBedRooms = userInput.toIntOrNull()
    }

    fun onEditTextNumberOfBathRoomsChanged(userInput: String) {
        numberOfBathRooms = userInput.toIntOrNull()
    }

    fun onAutocompleteCityChanged(userInput: String) {
        city = userInput

        viewModelScope.launch(coroutineDispatcherProvider.io) {
            autocompleteRepository.requestMyAutocompleteResponseOfCity(userInput)
        }
    }

    fun onAgentSpinnerItemClicked(selectedItem: RealEstateAgentSpinnerItemViewState) {
        agentIdInCharge = selectedItem.agentIdInCharge
    }

    fun onChipGuardClicked(checked: Boolean) {
        guard = checked
    }

    fun onChipGarageClicked(checked: Boolean) {
        garage = checked
    }

    fun onChipGardenClicked(checked: Boolean) {
        garden = checked
    }

    fun onChipElevatorClicked(checked: Boolean) {
        elevator = checked
    }

    fun onChipGroceryStoreNextByClicked(checked: Boolean) {
        groceryStoreNearby = checked
    }

    fun onChipRegisteredRecentlyClicked(checked: Boolean) {
        registeredRecently = checked
    }

    fun onChipSoldOutRecentlyClicked(checked: Boolean) {
        soldOutRecently = checked
    }

    fun onChipPhotosAvailableClicked(checked: Boolean) {
        photoAvailable = checked
    }

    fun onButtonApplyClicked(onFinished: () -> Unit) {
        val userSearchCriteria = SearchCriteria(
            type = type,
            city = city,
            agentIdInCharge = agentIdInCharge,
            numberOfBathrooms = numberOfBathRooms,
            numberOfBedrooms = numberOfBedRooms,
            minSquareMeter = minSurface,
            maxSquareMeter = maxSurface,
            minPrice = minPrice,
            maxPrice = maxPrice,
            garage = garage,
            garden = garden,
            guard = guard,
            elevator = elevator,
            groceryStoreNearby = groceryStoreNearby,
            soldOutRecently = soldOutRecently,
            registeredRecently = registeredRecently,
            photoAvailable = photoAvailable,
        )
        searchCriteriaRepository.setSearchCriteria(userSearchCriteria)
        onFinished()
    }

    fun onButtonResetClicked(onFinished: () -> Unit) {
        searchCriteriaRepository.resetSearchCriteria()
        onFinished()
    }


}
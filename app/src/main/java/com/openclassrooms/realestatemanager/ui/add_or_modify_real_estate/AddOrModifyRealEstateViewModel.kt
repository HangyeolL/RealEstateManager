package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.remote.model.autocomplete.PredictionResponse
import com.openclassrooms.realestatemanager.design_system.photo_carousel.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateFragment.Companion.KEY_REAL_ESTATE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddOrModifyRealEstateViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val agentRepository: AgentRepository,
    private val realEstateRepository: RealEstateRepository,
    private val autoCompleteRepository: AutocompleteRepository,
) : ViewModel() {

    val initialViewStateLiveData: LiveData<AddOrModifyRealEstateViewState> = liveData(Dispatchers.IO) {
        val realEstateId: Int? = savedStateHandle[KEY_REAL_ESTATE_ID]
        if (realEstateId != null) {
            coroutineScope {
                val realEstateAsync = async { realEstateRepository.getRealEstateById(realEstateId).first() }
                val allAgentsAsync = async { agentRepository.getAllAgents().first() }

                val typeSpinnerItemViewStateList = listOf(
                    AddOrModifyRealEstateTypeSpinnerItemViewState(
                        R.drawable.ic_baseline_house_24,
                        "House"
                    ),
                    AddOrModifyRealEstateTypeSpinnerItemViewState(
                        R.drawable.ic_baseline_apartment_24,
                        "Apartment"
                    ),
                    AddOrModifyRealEstateTypeSpinnerItemViewState(
                        R.drawable.ic_baseline_bed_24,
                        "Studio"
                    )
                )

                val allAgents = allAgentsAsync.await()

                val agentSpinnerItemViewStateList = allAgents.map { agentEntity ->
                    AddOrModifyRealEstateAgentSpinnerItemViewState(
                        agentIdInCharge = agentEntity.agentId,
                        agentNameInCharge = agentEntity.name,
                        agentPhoto = agentEntity.photoUrl
                    )
                }

                val realEstate = realEstateAsync.await()

                val photoListItemViewStateList = realEstate.realEstatePhotoLists.map { photoEntity ->
                    RealEstatePhotoItemViewState.Content(
                        photoId = photoEntity.photoId,
                        photoUrl = photoEntity.url,
                        photoDescription = photoEntity.description
                    )
                } + RealEstatePhotoItemViewState.AddRealEstatePhoto {

                    Log.d("Nino", "AddOrModifyRealEstateViewModel.onAddPhotoClicked() called")
                }

                // TODO need to make the texts to be able to be changed when user input comes
                emit(
                    AddOrModifyRealEstateViewState(
                        typeSpinnerItemViewStateList = typeSpinnerItemViewStateList,
                        agentSpinnerItemViewStateList = agentSpinnerItemViewStateList,
                        realEstatePhotoListItemViewStateList = photoListItemViewStateList,
                        address = realEstate.realEstateEntity.address,
                        numberOfRooms = realEstate.realEstateEntity.numberOfRooms.toString(),
                        numberOfBathrooms = realEstate.realEstateEntity.numberOfBathrooms.toString(),
                        numberOfBedrooms = realEstate.realEstateEntity.numberOfBedrooms.toString(),
                        squareMeter = realEstate.realEstateEntity.squareMeter.toString(),
                        marketSince = realEstate.realEstateEntity.marketSince,
                        price = realEstate.realEstateEntity.price.toString(),
                        garage = realEstate.realEstateEntity.garage,
                        guard = realEstate.realEstateEntity.guard,
                        garden = realEstate.realEstateEntity.garden,
                        elevator = realEstate.realEstateEntity.elevator,
                        groceryStoreNearby = realEstate.realEstateEntity.groceryStoreNearby,
                        isSoldOut = realEstate.realEstateEntity.isSoldOut,
                        dateOfSold = realEstate.realEstateEntity.dateOfSold,
                        description = realEstate.realEstateEntity.descriptionBody,
                    )
                )
            }
        }
    }

    val addressPredictionsLiveData: LiveData<List<PredictionResponse>> = liveData(Dispatchers.IO) {
        autoCompleteRepository.getMyAutocompleteResponse().collect { addressAutocompleteResponse ->
            emit(addressAutocompleteResponse?.predictions ?: emptyList())
        }
    }

    private var type: String? = null
    private var numberOfRooms: Int? = null
    private var numberOfBedRooms: Int? = null
    private var numberOfBathRooms: Int? = null
    private var sqm: Int? = null
    private var price: Int? = null
    private var marketSince: String? = null
    private var garage: Boolean? = null
    private var guard: Boolean? = null
    private var garden: Boolean? = null
    private var elevator: Boolean? = null
    private var groceryStoreNearby: Boolean? = null
    private var isSoldOut: Boolean? = null
    private var description: String? = null
    private var agentIdInCharge: Int? = null

    fun onTypeSpinnerItemSelected(selectedItem: AddOrModifyRealEstateTypeSpinnerItemViewState) {
        type = selectedItem.type
    }

    fun onEditTextAddressChanged(userInput: String?) {
        userInput?.let {
            autoCompleteRepository.requestMyAutocompleteResponse(userInput)
        }
    }

    fun onEditTextNumberOfRoomsChanged(userInput: String?) {
        numberOfRooms = userInput?.toIntOrNull()
    }

    fun onEditTextNumberOfBedRoomsChanged(userInput: String?) {
        numberOfBedRooms = userInput?.toIntOrNull()
    }

    fun onEditTextNumberOfBathRoomsChanged(userInput: String?) {
        numberOfBathRooms = userInput?.toIntOrNull()
    }

    fun onEditTextSqmChanged(userInput: String?) {
        sqm = userInput?.toIntOrNull()
    }

    fun onEditTextPriceChanged(userInput: String?) {
        price = userInput?.toIntOrNull()
    }

    fun onUserDateSet(year: Int, month: Int, day: Int) {
        marketSince = "$day/$month/$year"
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

    fun onChipIsSoldOutClicked(checked: Boolean) {
        isSoldOut = checked
    }

    fun onEditTextDescriptionChanged(userInput: String?) {
        description = userInput
    }

    fun onAgentSpinnerItemSelected(selectedItem: AddOrModifyRealEstateAgentSpinnerItemViewState) {
        agentIdInCharge = selectedItem.agentIdInCharge
    }

    fun onSaveButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            realEstateRepository.upsertRealEstate(
                RealEstateEntity(
                    type = type ?: return@launch,
                    descriptionBody = description ?: return@launch,
                    squareMeter = sqm ?: return@launch,
                    city = "",
                    price = price ?: return@launch,
                    numberOfRooms = numberOfRooms ?: return@launch,
                    numberOfBathrooms = numberOfBathRooms ?: return@launch,
                    numberOfBedrooms = numberOfBedRooms ?: return@launch,
                    address = "",
                    garage = garage ?: return@launch,
                    guard = guard ?: return@launch,
                    garden = garden ?: return@launch,
                    elevator = elevator ?: return@launch,
                    groceryStoreNearby = groceryStoreNearby ?: return@launch,
                    isSoldOut = isSoldOut ?: return@launch,
                    dateOfSold = null,
                    marketSince = marketSince ?: return@launch,
                    agentIdInCharge = agentIdInCharge ?: return@launch,
                    latLng = LatLng(0.0, 0.0)
                )
            )

            // TODO Hangyeol singleliveevent leave activity ?
        }
    }


}

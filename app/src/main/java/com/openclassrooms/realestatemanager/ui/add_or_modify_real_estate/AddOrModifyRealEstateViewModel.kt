package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.Application
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.design_system.photo_carousel.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateFragment.Companion.KEY_REAL_ESTATE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddOrModifyRealEstateViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
    private val agentRepository: AgentRepository,
    private val realEstateRepository: RealEstateRepository,
    private val autoCompleteRepository: AutocompleteRepository,
) : ViewModel() {

    val realEstateId: Int? = savedStateHandle[KEY_REAL_ESTATE_ID]

    val mediatorFlow: LiveData<AddOrModifyRealEstateViewState>

    private val allRealEstatesFlow = realEstateRepository.getRealEstatesWithPhotos()
    private val allAgentsFlow = agentRepository.getAllAgents()
    private val autocompleteResponseFlow = autoCompleteRepository.getMyAutocompleteResponse()

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


    // TODO Case : MODIFY
    init {
        if (realEstateId != null) {

            val selectedRealEstateFlow = realEstateRepository.getRealEstateById(realEstateId)
            val agentInChargeFlow = selectedRealEstateFlow
                .flatMapLatest { realEstateWithPhotos ->
                    agentRepository.getAgentById(realEstateWithPhotos.realEstateEntity.agentIdInCharge)
                }

            //TODO Doing this here doesn't feel right..? better way to do transformations ?
            mediatorFlow = liveData(Dispatchers.IO) {
                combine(
                    selectedRealEstateFlow,
                    agentInChargeFlow,
                    allRealEstatesFlow,
                    allAgentsFlow,
                    autocompleteResponseFlow,

                    ) { selectedRealEstate, agentInCharge, allRealEstates, allAgents, autocompleteResponse ->

                    val typeSpinnerItemViewStateList = listOf(
                        AddOrModifyRealEstateTypeSpinnerItemViewState(
                            ResourcesCompat.getDrawable(
                                application.resources,
                                R.drawable.ic_baseline_house_24,
                                null
                            ),
                            "House"
                        ),
                        AddOrModifyRealEstateTypeSpinnerItemViewState(
                            ResourcesCompat.getDrawable(
                                application.resources,
                                R.drawable.ic_baseline_apartment_24,
                                null
                            ),
                            "Apartment"
                        ),
                        AddOrModifyRealEstateTypeSpinnerItemViewState(
                            ResourcesCompat.getDrawable(
                                application.resources,
                                R.drawable.ic_baseline_bed_24,
                                null
                            ),
                            "Studio"
                        )
                    )

                    val agentSpinnerItemViewStateList = allAgents.map {
                        AddOrModifyRealEstateAgentSpinnerItemViewState(
                            it.agentId,
                            it.name,
                            it.photoUrl
                        )
                    }

                    val photoListItemViewStateList = selectedRealEstate.realEstatePhotoLists.map {
                        RealEstatePhotoItemViewState.Content(
                            it.photoId,
                            it.url,
                            it.description
                        )
                    } + RealEstatePhotoItemViewState.AddRealEstatePhoto {

                        Log.d("Nino", "AddOrModifyRealEstateViewModel.onAddPhotoClicked() called")
                    }

                    // TODO need to make the texts to be able to be changed when user input comes
                    emit(
                        AddOrModifyRealEstateViewState(
                            typeSpinnerItemViewStateList,
                            agentSpinnerItemViewStateList,
                            photoListItemViewStateList,
                            selectedRealEstate.realEstateEntity.address,
                            autocompleteResponse?.predictions ?: emptyList(),
                            selectedRealEstate.realEstateEntity.numberOfRooms,
                            selectedRealEstate.realEstateEntity.numberOfBathrooms,
                            selectedRealEstate.realEstateEntity.numberOfBedrooms,
                            selectedRealEstate.realEstateEntity.squareMeter,
                            selectedRealEstate.realEstateEntity.marketSince,
                            selectedRealEstate.realEstateEntity.price,
                            selectedRealEstate.realEstateEntity.garage,
                            selectedRealEstate.realEstateEntity.guard,
                            selectedRealEstate.realEstateEntity.garden,
                            selectedRealEstate.realEstateEntity.elevator,
                            selectedRealEstate.realEstateEntity.groceryStoreNearby,
                            selectedRealEstate.realEstateEntity.isSoldOut,
                            selectedRealEstate.realEstateEntity.dateOfSold,
                            selectedRealEstate.realEstateEntity.descriptionBody,
                        )
                    )

                }.collectLatest {

                }
            }

        } else { // TODO Case : ADD / normal case : blank view state
            mediatorFlow = liveData(Dispatchers.IO) {

            }
        }
    }

    fun onTypeSpinnerItemSelected(selectedItem: AddOrModifyRealEstateTypeSpinnerItemViewState) {
        type = selectedItem.type
    }

    fun onEditTextAddressChanged(userInput: String) {
        autoCompleteRepository.requestMyAutocompleteResponse(userInput)
    }

    fun onEditTextNumberOfRoomsChanged(userInput: Int) {
        numberOfRooms = userInput
    }

    fun onEditTextNumberOfBedRoomsChanged(userInput: Int) {
        numberOfBedRooms = userInput
    }

    fun onEditTextNumberOfBathRoomsChanged(userInput: Int) {
        numberOfBathRooms = userInput
    }

    fun onEditTextSqmChanged(userInput: Int) {
        sqm = userInput
    }

    fun onEditTextPriceChanged(userInput: Int) {
        price = userInput
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

    fun onEditTextDescriptionChanged(userInput: String) {
        description = userInput
    }

    fun onAgentSpinnerItemSelected(selectedItem: AddOrModifyRealEstateAgentSpinnerItemViewState) {
        agentIdInCharge = selectedItem.agentIdInCharge
    }

    fun onSaveButtonClicked() {
        if (type != null &&
            numberOfRooms != null &&
            numberOfBedRooms != null &&
            numberOfBathRooms != null &&
            sqm != null &&
            price != null &&
            marketSince != null &&
            garage != null &&
            guard != null &&
            garden != null &&
            elevator != null &&
            groceryStoreNearby != null &&
            isSoldOut != null &&
            description != null &&
            agentIdInCharge != null
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                // TODO How to put the values ? is there better way to do ?
//                realEstateRepository.upsertRealEstate(
//                    RealEstateEntity(
//                        type = type,
//                        descriptionBody = description,
//                        squareMeter = sqm,
//                        city = "",
//                        price = price,
//                        numberOfRooms = numberOfRooms,
//                        numberOfBathrooms = numberOfBathRooms,
//                        numberOfBedrooms = numberOfBedRooms,
//                        address = "",
//                        garage = garage,
//                        guard = guard,
//                        garden = garden,
//                        elevator = elevator,
//                        groceryStoreNearby = groceryStoreNearby,
//                        isSoldOut = isSoldOut,
//                        dateOfSold = null,
//                        marketSince = marketSince,
//                        agentIdInCharge = agentIdInCharge,
//                        latLng = LatLng()
//                    )
//                )
            }
        }
    }


}

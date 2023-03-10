package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.design_system.photo_carousel.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateFragment.Companion.KEY_REAL_ESTATE_ID
import com.openclassrooms.realestatemanager.ui.main.MainActivity
import com.openclassrooms.realestatemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddOrModifyRealEstateViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val agentRepository: AgentRepository,
    private val realEstateRepository: RealEstateRepository,
    private val autoCompleteRepository: AutocompleteRepository,
) : ViewModel() {

    val intentSingleLiveEvent: SingleLiveEvent<Intent> = SingleLiveEvent()
    val stringSingleLiveEvent: SingleLiveEvent<String> = SingleLiveEvent()

    val initialViewStateLiveData: LiveData<AddOrModifyRealEstateViewState> =
        liveData(Dispatchers.IO) {
            val realEstateId: Int? = savedStateHandle[KEY_REAL_ESTATE_ID]

            if (realEstateId != null) {
                coroutineScope {
                    val realEstateAsync =
                        async { realEstateRepository.getRealEstateById(realEstateId).first() }
                    val allAgentsAsync = async { agentRepository.getAllAgents().first() }

                    val typeSpinnerItemViewStateList = listOf(
                        AddOrModifyRealEstateTypeSpinnerItemViewState(
                            R.drawable.ic_baseline_house_24,
                            application.getString(R.string.house)
                        ),
                        AddOrModifyRealEstateTypeSpinnerItemViewState(
                            R.drawable.ic_baseline_apartment_24,
                            application.getString(R.string.apartment)
                        ),
                        AddOrModifyRealEstateTypeSpinnerItemViewState(
                            R.drawable.ic_baseline_bed_24,
                            application.getString(R.string.studio)
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

                    val photoListItemViewStateList =
                        realEstate.realEstatePhotoLists.map { photoEntity ->
                            RealEstatePhotoItemViewState.Content(
                                photoId = photoEntity.photoId,
                                photoUrl = photoEntity.url,
                                photoDescription = photoEntity.description
                            )
                        } + RealEstatePhotoItemViewState.AddRealEstatePhoto {
                            val intent = Intent("android.media.action.IMAGE_CAPTURE")
                            intentSingleLiveEvent.setValue(intent)

                            Log.d(
                                "Hangyeol",
                                "AddOrModifyRealEstateViewModel.onAddPhotoClicked() called"
                            )
                        }

                    emit(
                        AddOrModifyRealEstateViewState(
                            typeSpinnerItemViewStateList = typeSpinnerItemViewStateList,
                            agentSpinnerItemViewStateList = agentSpinnerItemViewStateList,
                            realEstatePhotoListItemViewStateList = photoListItemViewStateList,
                            address = realEstate.realEstateEntity.address,
                            city = realEstate.realEstateEntity.city,
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
            } else {
                coroutineScope() {
                    val allAgentsAsync = async { agentRepository.getAllAgents().first() }

                    val typeSpinnerItemViewStateList = listOf(
                        AddOrModifyRealEstateTypeSpinnerItemViewState(
                            R.drawable.ic_baseline_house_24,
                            application.getString(R.string.house)
                        ),
                        AddOrModifyRealEstateTypeSpinnerItemViewState(
                            R.drawable.ic_baseline_apartment_24,
                            application.getString(R.string.apartment)
                        ),
                        AddOrModifyRealEstateTypeSpinnerItemViewState(
                            R.drawable.ic_baseline_bed_24,
                            application.getString(R.string.studio)
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

                    val photoListItemViewStateList =
                        listOf(
                            RealEstatePhotoItemViewState.AddRealEstatePhoto {
                                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                                intentSingleLiveEvent.setValue(intent)

                                Log.d(
                                    "Hangyeol",
                                    "AddOrModifyRealEstateViewModel.onAddPhotoClicked() called"
                                )
                            }
                        )

                    emit(
                        AddOrModifyRealEstateViewState(
                            typeSpinnerItemViewStateList = typeSpinnerItemViewStateList,
                            agentSpinnerItemViewStateList = agentSpinnerItemViewStateList,
                            realEstatePhotoListItemViewStateList = photoListItemViewStateList,
                            address = "",
                            city = "",
                            numberOfRooms = "",
                            numberOfBathrooms = "",
                            numberOfBedrooms = "",
                            squareMeter = "",
                            marketSince = "",
                            price = "",
                            garage = false,
                            guard = false,
                            garden = false,
                            elevator = false,
                            groceryStoreNearby = false,
                            isSoldOut = false,
                            dateOfSold = null,
                            description = "",
                        )
                    )


                }


            }
        }




    val addressPredictionsLiveData: LiveData<List<AddOrModifyRealEstateAddressAndCityAutocompleteViewStateItem>> = liveData(Dispatchers.IO) {
        autoCompleteRepository.getAutocompleteEntitiesForAddress().collect { autocompleteEntities ->
            emit(
                autocompleteEntities.map { autocompleteEntity ->
                    AddOrModifyRealEstateAddressAndCityAutocompleteViewStateItem(
                        text = autocompleteEntity.text
                    ) {
                        Log.d("HL", "AddOrModifyRealEstateViewModel.address.onClick() called with $autocompleteEntity")
                    }
                }
            )
        }
    }

    val cityPredictionsLiveData: LiveData<List<AddOrModifyRealEstateAddressAndCityAutocompleteViewStateItem>> = liveData(Dispatchers.IO) {
        autoCompleteRepository.getAutocompleteEntitiesForCity().collect { autocompleteEntities ->
            emit(
                autocompleteEntities.map { autocompleteEntity ->
                    AddOrModifyRealEstateAddressAndCityAutocompleteViewStateItem(
                        text = autocompleteEntity.text
                    ) {
                        //TODO what is the point of having on click inside of viewState data class
                        Log.d("HL", "AddOrModifyRealEstateViewModel.city.onClick() called with $autocompleteEntity")
                    }
                }
            )
        }
    }
    private var address: String? = null
    private var city: String? = null
    private var type: String? = null
    private var numberOfRooms: Int? = null
    private var numberOfBedRooms: Int? = null
    private var numberOfBathRooms: Int? = null
    private var sqm: Int? = null
    private var price: Int? = null
    private var marketSince: String? = null
    private var garage: Boolean = false
    private var guard: Boolean = false
    private var garden: Boolean = false
    private var elevator: Boolean = false
    private var groceryStoreNearby: Boolean = false
    private var isSoldOut: Boolean = false
    private var dateOfSold: String? = null
    private var description: String? = null
    private var agentIdInCharge: Int? = null

    fun onTypeSpinnerItemClicked(selectedItem: AddOrModifyRealEstateTypeSpinnerItemViewState) {
        type = selectedItem.type
    }

    fun onAutocompleteAddressChanged(userInput: String?) {
        if (userInput != null) {
            autoCompleteRepository.requestMyAutocompleteResponseOfAddress(userInput)
        }

    }

    fun onAutocompleteAddressItemClicked(selectedItem: AddOrModifyRealEstateAddressAndCityAutocompleteViewStateItem) {
        address = selectedItem.text
    }

    fun onEditTextCityChanged(userInput: String?) {
        if (userInput != null) {
            autoCompleteRepository.requestMyAutocompleteResponseOfCity(userInput)
        }
        city = userInput
    }

    fun onAutocompleteCityItemClicked(selectedItem: AddOrModifyRealEstateAddressAndCityAutocompleteViewStateItem) {
        city = selectedItem.text
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

    fun onDefaultMarketSinceValueSet(date: String) {
        marketSince = date
    }

    fun onUserMarketSinceDateSet(year: Int, month: Int, day: Int) {
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

    fun onUserDateOfSoldSet(year: Int, month: Int, day: Int) {
        dateOfSold = "$day/$month/$year"
    }

    fun onEditTextDescriptionChanged(userInput: String?) {
        description = userInput
    }

    fun onAgentSpinnerItemClicked(selectedItem: AddOrModifyRealEstateAgentSpinnerItemViewState) {
        agentIdInCharge = selectedItem.agentIdInCharge
    }

    fun onSaveButtonClicked() {
        if (
            type != null &&
            address != null &&
            city != null &&
            numberOfRooms != null &&
            numberOfBedRooms != null &&
            numberOfBathRooms != null &&
            sqm != null &&
            price != null &&
            marketSince != null &&
            description != null &&
            agentIdInCharge != null
        ) {

            viewModelScope.launch(Dispatchers.IO) {
                realEstateRepository.upsertRealEstate(
                    RealEstateEntity(
                        type = type ?: return@launch,
                        descriptionBody = description ?: return@launch,
                        squareMeter = sqm ?: return@launch,
                        city = city ?: return@launch,
                        price = price ?: return@launch,
                        numberOfRooms = numberOfRooms ?: return@launch,
                        numberOfBathrooms = numberOfBathRooms ?: return@launch,
                        numberOfBedrooms = numberOfBedRooms ?: return@launch,
                        address = address ?: return@launch,
                        garage = garage,
                        guard = guard,
                        garden = garden,
                        elevator = elevator,
                        groceryStoreNearby = groceryStoreNearby,
                        isSoldOut = isSoldOut,
                        dateOfSold = dateOfSold,
                        marketSince = marketSince ?: return@launch,
                        agentIdInCharge = agentIdInCharge ?: return@launch,
                        latLng = LatLng(0.0, 0.0)
                    )
                )
            }
            intentSingleLiveEvent.setValue(MainActivity.navigate(application))
        } else {
            stringSingleLiveEvent.setValue(application.getString(R.string.please_fill_out_all_the_forms))
        }
    }


}

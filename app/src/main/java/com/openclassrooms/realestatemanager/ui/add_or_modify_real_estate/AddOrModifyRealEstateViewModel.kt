package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstatePhotoEntity
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.domain.geocoding.GeocodingRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AddOrModifyRealEstateViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val agentRepository: AgentRepository,
    private val realEstateRepository: RealEstateRepository,
    private val autoCompleteRepository: AutocompleteRepository,
    private val geocodingRepository: GeocodingRepository,
) : ViewModel() {

    companion object {
        private const val DEFAULT_REAL_ESTATE_ID = 0
    }

    val realEstateId: Int? = savedStateHandle.get<Int>("realEstateId")

    val stringSingleLiveEvent: SingleLiveEvent<String> = SingleLiveEvent()

    val initialViewStateLiveData: LiveData<AddOrModifyRealEstateViewState> =
        liveData(Dispatchers.IO) {
            Log.d("HG", "AddOrModifyReceived:$realEstateId")

            //TODO why realEstateAsync doesn't get the photoList with new elements added ?
            if (realEstateId != DEFAULT_REAL_ESTATE_ID && realEstateId != null) {
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
                        }.plus(RealEstatePhotoItemViewState.AddRealEstatePhoto)

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
            } else if (realEstateId == DEFAULT_REAL_ESTATE_ID) {
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
                            RealEstatePhotoItemViewState.AddRealEstatePhoto
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


    val addressPredictionsLiveData: LiveData<List<AddOrModifyRealEstateAutocompleteItemViewState>> =
        liveData(Dispatchers.IO) {
            autoCompleteRepository.getAutocompleteEntitiesForAddress()
                .collect { autocompleteEntities ->
                    emit(
                        autocompleteEntities.map { autocompleteEntity ->
                            AddOrModifyRealEstateAutocompleteItemViewState(
                                text = autocompleteEntity.text
                            )
                        }
                    )
                }
        }

    val cityPredictionsLiveData: LiveData<List<AddOrModifyRealEstateAutocompleteItemViewState>> =
        liveData(Dispatchers.IO) {
            autoCompleteRepository.getAutocompleteEntitiesForCity()
                .collect { autocompleteEntities ->
                    emit(
                        autocompleteEntities.map { autocompleteEntity ->
                            AddOrModifyRealEstateAutocompleteItemViewState(
                                text = autocompleteEntity.text
                            )
                        }
                    )
                }
        }

    private var address: String? = null
    private var latLng: LatLng? = null
    private var city: String? = null
    private var type: String? = null
    private var numberOfRooms: Int? = null
    private var numberOfBedRooms: Int? = null
    private var numberOfBathRooms: Int? = null
    private var sqm: Int? = null
    private var price: Int? = null
    private var garage: Boolean = false
    private var guard: Boolean = false
    private var garden: Boolean = false
    private var elevator: Boolean = false
    private var groceryStoreNearby: Boolean = false
    private var isSoldOut: Boolean = false
    private var marketSince: String? = null
    private var dateOfSold: String? = null
    private var description: String? = null
    private var agentIdInCharge: Int? = null

    fun onTypeSpinnerItemClicked(selectedItem: AddOrModifyRealEstateTypeSpinnerItemViewState) {
        type = selectedItem.type
    }

    fun onAutocompleteAddressChanged(userInput: String?) {
        if (userInput != null) {
            address = userInput

            viewModelScope.launch(Dispatchers.IO) {
                autoCompleteRepository.requestMyAutocompleteResponseOfAddress(userInput)
            }
        }
    }

    fun onAutocompleteAddressItemClicked(selectedItem: AddOrModifyRealEstateAutocompleteItemViewState) {
        address = selectedItem.text

        viewModelScope.launch(Dispatchers.IO) {
            latLng = geocodingRepository.requestMyGeocodingResponse(selectedItem.text)?.latLng
            Log.d("HG:Geocoding", "$latLng")
        }
    }

    fun onEditTextCityChanged(userInput: String?) {
        if (userInput != null) {
            city = userInput

            viewModelScope.launch(Dispatchers.IO) {
                autoCompleteRepository.requestMyAutocompleteResponseOfCity(userInput)
            }
        }
    }

    fun onAutocompleteCityItemClicked(selectedItem: AddOrModifyRealEstateAutocompleteItemViewState) {
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

    fun onUserMarketSinceDateSet(date: String) {
        marketSince = date
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

    fun onUserDateOfSoldSet(date: String) {
        dateOfSold = date
    }

    fun onEditTextDescriptionChanged(userInput: String?) {
        description = userInput
    }

    fun onAgentSpinnerItemClicked(selectedItem: AddOrModifyRealEstateAgentSpinnerItemViewState) {
        agentIdInCharge = selectedItem.agentIdInCharge
    }

    private var realEstateIdOfPhoto: Long = DEFAULT_REAL_ESTATE_ID.toLong()
    private var picUriToString: String? = null
    private var photoDescription: String? = null

    fun onRealEstateInserted(autoGeneratedRealEstateId: Long) {
        realEstateIdOfPhoto = autoGeneratedRealEstateId
    }

    fun onPicUriToStringSet(string: String) {
        picUriToString = string
    }

    fun photoDescriptionSet(string: String) {
        photoDescription = string
    }

    fun onSaveButtonClicked(): Boolean {
        val capturedAddress = address

        if (
            type != null
            && capturedAddress != null
            && city != null
            && numberOfRooms != null
            && numberOfBedRooms != null
            && numberOfBathRooms != null
            && sqm != null
            && price != null
            && marketSince != null
            && description != null
            && agentIdInCharge != null
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
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
                            latLng = latLng ?: return@launch
                        )
                    ) { autoGeneratedRealEstateId ->
                        onRealEstateInserted(autoGeneratedRealEstateId)
                    }
                    Log.d("HL", "upsert RealEstate with success")
                } catch (e: Exception) {
                    Log.d("HL", "upsert RealEstate Failed : ${e.stackTrace}")
                }

                if (picUriToString != null) {
                    try {
                        realEstateRepository.insertRealEstatePhoto(
                            RealEstatePhotoEntity(
                                realEstateIdOfPhoto = realEstateIdOfPhoto,
                                url = picUriToString ?: return@launch,
                                description = photoDescription,
                            )
                        ) {}
                        Log.d("HL", "upsert RealEstatePhoto with success")
                    } catch (e: Exception) {
                        Log.d("HL", "upsert RealEstatePhoto Failed : ${e.stackTrace}")
                    }

                }

//                if (realEstateId != null) {
//                    Log.d("HL", "updating photoEntity: photoId = $photoId")
//                    realEstateRepository.updateRealEstateIdOfPhotoEntity(
//                        autoGeneratedPhotoId = photoId ?: return@launch,
//                        realEstateEntityId = realEstateId
//                    )
//                }

            }
            return true
        } else {
            stringSingleLiveEvent.setValue(application.getString(R.string.please_fill_out_all_the_forms))
            return false
        }
    }

    //Method from AddPhotoDialogFragment
//    fun onAddPhotoButtonOkClicked(realEstateId: Int, picUriToString: String, description: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            if (realEstateId != DEFAULT_REAL_ESTATE_ID) {
//                realEstateRepository.insertRealEstatePhoto(
//                    RealEstatePhotoEntity(
//                        realEstateIdOfPhoto = realEstateId,
//                        url = picUriToString,
//                        description = description,
//                    )
//                ) { photoId ->
//                    onPhotoInserted(photoId)
//                    Log.d("HL", "onAddPhotoButtonOkClicked. insertedPhotoId=$photoId")
//                }
//            } else {
//                realEstateRepository.insertRealEstatePhoto(
//                    RealEstatePhotoEntity(
//                        realEstateIdOfPhoto = realEstateId,
//                        url = picUriToString,
//                        description = description,
//                    )
//                ) { photoId ->
//                    onPhotoInserted(photoId)
//                    Log.d("HL", "onAddPhotoButtonOkClicked. insertedPhotoId=$photoId")
//                }
//            }
//        }
//    }
}



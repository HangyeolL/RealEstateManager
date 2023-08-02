package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstatePhotoEntity
import com.openclassrooms.realestatemanager.design_system.autocomplete_text_view.AutocompleteTextViewState
import com.openclassrooms.realestatemanager.design_system.real_estate_agent.RealEstateAgentSpinnerItemViewState
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.design_system.real_estate_type.RealEstateTypeSpinnerItemViewState
import com.openclassrooms.realestatemanager.domain.CoroutineDispatcherProvider
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.domain.geocoding.GeocodingRepository
import com.openclassrooms.realestatemanager.domain.realestate.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class AddOrModifyRealEstateViewModel @Inject constructor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val application: Application,
    savedStateHandle: SavedStateHandle,
    private val agentRepository: AgentRepository,
    private val realEstateRepository: RealEstateRepository,
    private val autoCompleteRepository: AutocompleteRepository,
    private val geocodingRepository: GeocodingRepository,
) : ViewModel() {

    companion object {
        private const val DEFAULT_REAL_ESTATE_ID = 0
        private const val REAL_ESTATE_ID_KEY = "realEstateId"
    }

    val realEstateId: Int? = savedStateHandle.get<Int>(REAL_ESTATE_ID_KEY)

    val stringSingleLiveEvent: SingleLiveEvent<String> = SingleLiveEvent()

    val initialViewStateLiveData: LiveData<AddOrModifyRealEstateViewState> =
        liveData(coroutineDispatcherProvider.io) {
            Log.d("HG", "AddOrModifyReceived:$realEstateId")

            //TODO why realEstateAsync doesn't get the photoList with new elements added ?
            if (realEstateId != DEFAULT_REAL_ESTATE_ID && realEstateId != null) {
                coroutineScope {
                    val realEstate =
                        async { realEstateRepository.getRealEstateWithPhotosById(realEstateId).first() }.await()
                    val allAgents = async { agentRepository.getAllAgents().first() }.await()

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

                    val agentSpinnerItemViewStateList = allAgents.map { agentEntity ->
                        RealEstateAgentSpinnerItemViewState(
                            agentIdInCharge = agentEntity.agentId,
                            agentNameInCharge = agentEntity.name,
                            agentPhoto = agentEntity.photoUrl
                        )
                    }

                    val photoListItemViewStateList =
                        realEstate.realEstatePhotoLists.map { photoEntity ->
                            RealEstatePhotoItemViewState.Content(
                                photoId = photoEntity.photoId,
                                photoUrl = photoEntity.url,
                                photoDescription = photoEntity.description
                            )
                        }.plus(RealEstatePhotoItemViewState.AddRealEstatePhoto)

                    emit(
                        AddOrModifyRealEstateViewState.Modification(
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
                coroutineScope {
                    val allAgentsAsync = async { agentRepository.getAllAgents().first() }

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

                    val allAgents = allAgentsAsync.await()

                    val agentSpinnerItemViewStateList = allAgents.map { agentEntity ->
                        RealEstateAgentSpinnerItemViewState(
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
                        AddOrModifyRealEstateViewState.Creation(
                            typeSpinnerItemViewStateList = typeSpinnerItemViewStateList,
                            agentSpinnerItemViewStateList = agentSpinnerItemViewStateList,
                            realEstatePhotoListItemViewStateList = photoListItemViewStateList,
                        )
                    )


                }


            }
        }


    val addressPredictionsLiveData: LiveData<List<AutocompleteTextViewState>> =
        liveData(coroutineDispatcherProvider.io) {
            autoCompleteRepository.getAutocompleteEntitiesForAddress()
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

    val cityPredictionsLiveData: LiveData<List<AutocompleteTextViewState>> =
        liveData(coroutineDispatcherProvider.io) {
            autoCompleteRepository.getAutocompleteEntitiesForCity()
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

    fun onTypeSpinnerItemClicked(selectedItem: RealEstateTypeSpinnerItemViewState) {
        type = selectedItem.type
    }

    fun onAutocompleteAddressChanged(userInput: String?) {
        if (userInput != null) {
            address = userInput

            viewModelScope.launch(coroutineDispatcherProvider.io) {
                autoCompleteRepository.requestMyAutocompleteResponseOfAddress(userInput)

                latLng = geocodingRepository.requestMyGeocodingResponse(userInput)?.latLng
                Log.d("HG:Geocoding", "$latLng")
            }
        }
    }

    fun onAutocompleteAddressItemClicked(selectedItem: AutocompleteTextViewState) {
        address = selectedItem.text
    }

    fun onEditTextCityChanged(userInput: String?) {
        if (userInput != null) {
            city = userInput

            viewModelScope.launch(coroutineDispatcherProvider.io) {
                autoCompleteRepository.requestMyAutocompleteResponseOfCity(userInput)
            }
        }
    }

    fun onAutocompleteCityItemClicked(selectedItem: AutocompleteTextViewState) {
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

    fun onAgentSpinnerItemClicked(selectedItem: RealEstateAgentSpinnerItemViewState) {
        agentIdInCharge = selectedItem.agentIdInCharge
    }

    private var realEstateIdOfPhoto: Long = DEFAULT_REAL_ESTATE_ID.toLong()
    private var picUriToString: String? = null
    private var photoDescription: String? = null

    private fun onRealEstateInserted(autoGeneratedRealEstateId: Long) {
        realEstateIdOfPhoto = autoGeneratedRealEstateId
    }

    fun onPicUriToStringSet(string: String) {
        picUriToString = string
    }

    fun photoDescriptionSet(string: String) {
        photoDescription = string
    }

    fun onSaveButtonClicked(onFinished: () -> Unit) {
        if (realEstateId == DEFAULT_REAL_ESTATE_ID) {
            // CASE : Insert new entity
            viewModelScope.launch(coroutineDispatcherProvider.io) {
                try {
                    realEstateRepository.insertRealEstate(
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
                        Log.d("HL", "autoGeneratedRealEstateId: $autoGeneratedRealEstateId")
                    }
                    Log.d("HL", "insert RealEstate with success")
                } catch (e: Exception) {
                    Log.d("HL", "insert RealEstate Failed : ${e.stackTrace}")
                }

                if (picUriToString != null) {
                    try {
                        realEstateRepository.insertRealEstatePhoto(
                            RealEstatePhotoEntity(
                                realEstateIdOfPhoto = realEstateIdOfPhoto,
                                url = picUriToString ?: return@launch,
                                description = photoDescription,
                            )
                        )
                        Log.d("HL", "insert RealEstatePhoto with success")
                    } catch (e: Exception) {
                        Log.d("HL", "insert RealEstatePhoto Failed : ${e.stackTrace}")
                    }
                }

                withContext(coroutineDispatcherProvider.main) {
                    onFinished()
                }
            }
        } else {
            // CASE : Modify an existing entity
            viewModelScope.launch(coroutineDispatcherProvider.io) {
                try {
                    Log.d("HL", "Before calling update RealEstate")

                    val realEstateEntity = RealEstateEntity(
                        realEstateId = realEstateId ?: return@launch,
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
                    Log.d("HL", "Trying to update entity : $realEstateEntity")
                    realEstateRepository.updateRealEstate(
                        realEstateEntity
                    )
                    Log.d("HL", "update RealEstate with success")
                } catch (e: Exception) {
                    Log.d("HL", "update RealEstate Failed : ${e.stackTrace}")

                }

                //TODO realEstateId should be properly inserted in this case not IdOfPhoto !!
                if (picUriToString != null) {
                    try {
                        realEstateRepository.insertRealEstatePhoto(
                            RealEstatePhotoEntity(
                                realEstateIdOfPhoto = realEstateIdOfPhoto,
                                url = picUriToString ?: return@launch,
                                description = photoDescription,
                            )
                        )
                        Log.d("HL", "insert RealEstatePhoto with success")
                    } catch (e: Exception) {
                        Log.d("HL", "insert RealEstatePhoto Failed : ${e.stackTrace}")
                    }
                }

                withContext(coroutineDispatcherProvider.main) {
                    onFinished()
                }
            }

        }
    }
}
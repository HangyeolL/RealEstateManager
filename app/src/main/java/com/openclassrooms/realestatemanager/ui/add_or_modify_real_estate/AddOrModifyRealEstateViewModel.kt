package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.Application
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import com.bumptech.glide.Glide.init
import com.openclassrooms.realestatemanager.R
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

    private var realEstateType: String? = null
    private var numberOfRooms: Int? = null

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
        realEstateType = selectedItem.type
    }

    fun onEditTextAddressChanged(userInput: String) {
        autoCompleteRepository.requestMyAutocompleteResponse(userInput)
    }

    fun onEditTextNumberOfRoomsChanged(userInput: Int) {
        numberOfRooms = userInput
    }

    fun onEditTextNumberOfBedRoomsChanged(toInt: Int) {
        TODO("Not yet implemented")
    }

    fun onEditTextNumberOfBathRoomsChanged(toInt: Int) {
        TODO("Not yet implemented")
    }

    fun onEditTextSqmChanged(toInt: Int) {

    }

    fun onEditTextPriceChanged(toInt: Int) {

    }


}
//                typeSpinnerItemViewStateList.add(
//                    AddOrModifyRealEstateTypeSpinnerItemViewState(
//                        ResourcesCompat.getDrawable(
//                            application.resources,
//                            R.drawable.ic_baseline_apartment_24,
//                            null
//                        ),
//                        "Apartment"
//                    )
//                )
//                typeSpinnerItemViewStateList.add(
//                    AddOrModifyRealEstateTypeSpinnerItemViewState(
//                        ResourcesCompat.getDrawable(
//                            application.resources,
//                            R.drawable.ic_baseline_bed_24,
//                            null
//                        ),
//                        "Studio"
//                    )
//                )
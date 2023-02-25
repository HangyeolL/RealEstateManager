package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.Application
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.design_system.photo_carousel.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
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
    private val application: Application,
    private val agentRepository: AgentRepository,
    private val realEstateRepository: RealEstateRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val mediatorFlow: LiveData<AddOrModifyRealEstateViewState>

    init {
        val realEstateId: Int? = savedStateHandle[KEY_REAL_ESTATE_ID]

        // TODO Case : MODIFY
        if (realEstateId != null) {

            //TODO Doing this here doesn't feel right..? better way to do transformations ?

            val allRealEstatesFlow = realEstateRepository.getRealEstatesWithPhotos()
            val allAgentsFlow = agentRepository.getAllAgents()
            val selectedRealEstateFlow = realEstateRepository.getRealEstateById(realEstateId)
            val agentInChargeFlow = selectedRealEstateFlow
                .flatMapLatest { realEstateWithPhotos ->
                    agentRepository.getAgentById(realEstateWithPhotos.realEstateEntity.agentIdInCharge)
                }

            mediatorFlow = liveData(Dispatchers.IO) {
                combine(
                    selectedRealEstateFlow,
                    agentInChargeFlow,
                    allRealEstatesFlow,
                    allAgentsFlow,
                ) { realEstate, agentInCharge, allRealEstates, allAgents ->

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

                    val photoListItemViewStateList = realEstate.realEstatePhotoLists.map {
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
                            realEstate.realEstateEntity.address,
                            realEstate.realEstateEntity.numberOfRooms,
                            realEstate.realEstateEntity.numberOfBathrooms,
                            realEstate.realEstateEntity.numberOfBedrooms,
                            realEstate.realEstateEntity.squareMeter,
                            realEstate.realEstateEntity.marketSince,
                            realEstate.realEstateEntity.price,
                            realEstate.realEstateEntity.garage,
                            realEstate.realEstateEntity.guard,
                            realEstate.realEstateEntity.garden,
                            realEstate.realEstateEntity.elevator,
                            realEstate.realEstateEntity.groceryStoreNearby,
                            realEstate.realEstateEntity.isSoldOut,
                            realEstate.realEstateEntity.dateOfSold,
                            realEstate.realEstateEntity.descriptionBody,
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
package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.Application
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import com.bumptech.glide.Glide.init
import com.openclassrooms.realestatemanager.R
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

    lateinit var mediatorFlow: LiveData<AddOrModifyRealEstateViewState>

    init {
        val realEstateId: Int? = savedStateHandle[KEY_REAL_ESTATE_ID]

        // TODO Case : MODIFY
        if (realEstateId != null) {

            //TODO Doing this here doesn't feel right..? better way to do transformations ?

            val realEstateFlow = realEstateRepository.getRealEstateById(realEstateId)
            val agentInChargeFlow = realEstateFlow
                .flatMapLatest { realEstateWithPhotos ->
                    agentRepository.getAgentById(realEstateWithPhotos.realEstateEntity.agentIdInCharge)
                }

            mediatorFlow = liveData(Dispatchers.IO) {
                combine(
                    realEstateFlow,
                    agentInChargeFlow
                ) { realEstate, agentInCharge ->

                    val typeSpinnerItemViewStateList = listOf(
                        AddOrModifyRealEstateTypeSpinnerItemViewState(
                            ResourcesCompat.getDrawable(
                                application.resources,
                                when (realEstate.realEstateEntity.type) {
                                    "House" -> R.drawable.ic_baseline_house_24
                                    "Studio" -> R.drawable.ic_baseline_bed_24
                                    "Apartment" -> R.drawable.ic_baseline_apartment_24
                                    else -> {
                                        null
                                    }
                                } as Int,
                                null
                            ),
                            realEstate.realEstateEntity.type
                        )
                    )

                    val agentSpinnerItemViewStateList = listOf(
                        AddOrModifyRealEstateAgentSpinnerItemViewState(
                            agentInCharge.agentId,
                            agentInCharge.name,
                            agentInCharge.photoUrl
                        )
                    )

                    val photoListItemViewStateList =
                        ArrayList<AddOrModifyRealEstatePhotoListItemViewState>()

                    realEstate.realEstatePhotoLists.forEach {
                        photoListItemViewStateList.add(
                            AddOrModifyRealEstatePhotoListItemViewState(
                                it.photoId,
                                it.url,
                                it.description
                            )
                        )
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
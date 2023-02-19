package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.app.Application
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateFragment.Companion.KEY_REAL_ESTATE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddOrModifyRealEstateViewModel @Inject constructor(
    private val application: Application,
    private val agentRepository: AgentRepository,
    private val realEstateRepository: RealEstateRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    init {
        val realEstateId: Int? = savedStateHandle[KEY_REAL_ESTATE_ID]

        // TODO Case : MODIFY
        if (realEstateId != null) {
            val realEstateFlow = realEstateRepository.getRealEstateById(realEstateId)

            val agentsListFlow = agentRepository.getAllAgents()

            val agentInChargeFlow = realEstateFlow
                .flatMapLatest { realEstateWithPhotos ->
                    agentRepository.getAgentById(realEstateWithPhotos.realEstateEntity.agentIdInCharge)
                }

            combine(
                realEstateFlow,
                agentsListFlow
            ) { realEstate, agentsList ->

                //TODO Doing this here doesn't feel right..?
                val typeSpinnerItemViewStateList = ArrayList<AddOrModifyRealEstateTypeSpinnerItemViewState>()
                typeSpinnerItemViewStateList.add(
                    AddOrModifyRealEstateTypeSpinnerItemViewState(
                        ResourcesCompat.getDrawable(
                            application.resources,
                            R.drawable.ic_baseline_house_24,
                            null
                        ),
                        "House"
                    )
                )
                typeSpinnerItemViewStateList.add(
                    AddOrModifyRealEstateTypeSpinnerItemViewState(
                        ResourcesCompat.getDrawable(
                            application.resources,
                            R.drawable.ic_baseline_apartment_24,
                            null
                        ),
                        "Apartment"
                    )
                )
                typeSpinnerItemViewStateList.add(
                    AddOrModifyRealEstateTypeSpinnerItemViewState(
                        ResourcesCompat.getDrawable(
                            application.resources,
                            R.drawable.ic_baseline_bed_24,
                            null
                        ),
                        "Studio"
                    )
                )

                val agentSpinnerItemViewStateList = ArrayList<AddOrModifyRealEstateAgentSpinnerItemViewState>()
                agentsList.forEach {
                    agentSpinnerItemViewStateList.add(
                        AddOrModifyRealEstateAgentSpinnerItemViewState(
                            it.agentId,
                            it.name,
                            it.photoUrl
                        )
                    )
                }

                val photoListItemViewState = ArrayList<AddOrModifyRealEstatePhotoListItemViewState>()
                realEstate.realEstatePhotoLists.forEach {
                    photoListItemViewState.add(
                        AddOrModifyRealEstatePhotoListItemViewState(
                            it.photoId,
                            it.url,
                            it.description
                        )
                    )
                }

            }

            viewModelScope.launch(Dispatchers.IO) {
                // TODO Hangyeol Query RealEstateRepository to fill the view fields
            }
        } else { // TODO Case : ADD

        }
    }
}
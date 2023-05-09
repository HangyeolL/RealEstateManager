package com.openclassrooms.realestatemanager.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.detail_map.DetailMapViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val application: Application,
    currentRealEstateRepository: CurrentRealEstateRepository,
    realEstateRepository: RealEstateRepository,
    agentRepository: AgentRepository,
) : ViewModel() {

    var selectedRealEstateId: Int = currentRealEstateRepository.getCurrentRealEstateId().value

    private val currentRealEstateFlow = currentRealEstateRepository.getCurrentRealEstateId()
        .filterNotNull()
        .flatMapLatest { currentId ->
            realEstateRepository.getRealEstateWithPhotosById(currentId)
        }

    private val allAgentsListFlow = agentRepository.getAllAgents()

    val detailViewStateLiveData: LiveData<DetailViewState> = liveData(Dispatchers.IO) {
        combine(
            currentRealEstateFlow,
            allAgentsListFlow,
        ) { currentRealEstate, allAgentsList ->
            emit(
                DetailViewState(
                    itemViewStateList = currentRealEstate.realEstatePhotoLists.map {
                        RealEstatePhotoItemViewState.Content(
                            it.photoId,
                            it.url,
                            it.description
                        )
                    },

                    agentName = allAgentsList.find { agent ->
                        agent.agentId == currentRealEstate.realEstateEntity.agentIdInCharge
                    }?.name ?: application.getString(R.string.none),

                    agentPhotoUrl = allAgentsList.find { agent ->
                        agent.agentId == currentRealEstate.realEstateEntity.agentIdInCharge
                    }?.photoUrl ?: application.getString(R.string.none),

                    descriptionBody = currentRealEstate.realEstateEntity.descriptionBody,
                    squareMeter = currentRealEstate.realEstateEntity.squareMeter,
                    numberOfBedrooms = currentRealEstate.realEstateEntity.numberOfBedrooms,
                    numberOfRooms = currentRealEstate.realEstateEntity.numberOfRooms,
                    numberOfBathrooms = currentRealEstate.realEstateEntity.numberOfBathrooms,
                    address = currentRealEstate.realEstateEntity.address,

                    isViewVisible = true
                )
            )
        }.collectLatest {}
    }

    val mapViewStateLiveData: LiveData<DetailMapViewState> = liveData(Dispatchers.IO) {
        currentRealEstateFlow.collectLatest { currentRealEstate ->
            emit(
                DetailMapViewState(
                    currentRealEstate.realEstateEntity.latLng
                )
            )
        }
    }

}

package com.openclassrooms.realestatemanager.ui.detail

import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.data.model.AgentEntity
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    private val currentRealEstateRepository: CurrentRealEstateRepository,
    private val agentRepository: AgentRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {

    val detailViewState: LiveData<DetailViewState> = liveData(Dispatchers.IO) {
        currentRealEstateRepository.getCurrentRealEstateId()
            .filterNotNull()
            .flatMapLatest { currentRealEstateId ->
                realEstateRepository.getRealEstateById(currentRealEstateId)
            }.collect {
                emit(
                    DetailViewState(
                        it.descriptionBody,
                        it.squareMeter,
                        it.numberOfRooms,
                        it.numberOfBathrooms,
                        it.numberOfBedrooms,
                        it.address,
                        it.latLng,
                        "Agent Name",
                        true
                    )
                )
            }
    }

    private val realEstateFlow = currentRealEstateRepository.getCurrentRealEstateId()
        .filterNotNull()
        .flatMapLatest { currentRealEstateId ->
            realEstateRepository.getRealEstateById(currentRealEstateId)
        }

    private val agentListFlow = agentRepository.getAllAgents()

    private val locationFlow = locationRepository.

    val mediatorFlow: LiveData<DetailViewState> = liveData(Dispatchers.IO) {
            combine(realEstateFlow, agentListFlow) { realEstate, agentList ->
                emit(

                )
            }.collect()
        }

    fun mapItem(realEstateEntity: RealEstateEntity, agentEntityList: List<AgentEntity>) : DetailViewState {
        for (agentEntity in agentEntityList) {
            if (agentEntity.id.equals(realEstateEntity.agentIdInCharge)) {
                return DetailViewState(
                    realEstateEntity.descriptionBody,
                    realEstateEntity.squareMeter,
                    realEstateEntity.numberOfRooms,
                    realEstateEntity.numberOfBathrooms,
                    realEstateEntity.numberOfBedrooms,
                    realEstateEntity.address,
                    LatLng(11.11,11.11),
                    agentEntity.name,
                    true
                )
            }
        }
    }


}
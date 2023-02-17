package com.openclassrooms.realestatemanager.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.model.AgentEntity
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val application: Application,
    private val locationRepository: LocationRepository,
    realEstateRepository: RealEstateRepository,
    currentRealEstateRepository: CurrentRealEstateRepository,
    agentRepository: AgentRepository,
) : ViewModel() {

//    val detailViewState: LiveData<DetailViewState> = liveData(Dispatchers.IO) {
//        currentRealEstateRepository.getCurrentRealEstateIdStateFlow()
//            .filterNotNull()
//            .flatMapLatest { currentRealEstateId ->
//                realEstateRepository.getRealEstateById(currentRealEstateId)
//            }.collect {
//                emit(
//                    DetailViewState(
//                        it.descriptionBody,
//                        it.squareMeter,
//                        it.numberOfRooms,
//                        it.numberOfBathrooms,
//                        it.numberOfBedrooms,
//                        it.address,
//                        it.latLng,
//                        "Agent Name",
//                        true
//                    )
//                )
//            }
//    }

    private val realEstateFlow = currentRealEstateRepository.getCurrentRealEstateIdStateFlow()
        .filterNotNull()
        .flatMapLatest { currentRealEstateId ->
            realEstateRepository.getRealEstateById(currentRealEstateId)
        }

    private val agentListFlow = agentRepository.getAllAgents()

    private val locationFlow = locationRepository.getLocationStateFlow()

    val mediatorFlow: LiveData<DetailViewState> = liveData(Dispatchers.IO) {
        combine(
            realEstateFlow,
            agentListFlow,
            locationFlow
        ) { realEstateWithPhotos, agentList, locationFlow ->

            val userLatLang: LatLng? =
                if (locationFlow != null) {
                    LatLng(locationFlow.latitude, locationFlow.longitude)
                } else {
                    null
                }

            val agentInCharge: AgentEntity? =
                agentList.find {
                    it.agentId == realEstateWithPhotos.realEstateEntity.agentIdInCharge
                }

            emit(
                DetailViewState(
                    realEstateWithPhotos.realEstateEntity.descriptionBody,
                    realEstateWithPhotos.realEstateEntity.squareMeter,
                    realEstateWithPhotos.realEstateEntity.numberOfRooms,
                    realEstateWithPhotos.realEstateEntity.numberOfBathrooms,
                    realEstateWithPhotos.realEstateEntity.numberOfBedrooms,
                    realEstateWithPhotos.realEstateEntity.address,
                    userLatLang,
                    agentInCharge?.name ?: application.getString(R.string.none),
                    true
                )
            )


        }.collectLatest() {

        }
    }

    fun startLocationRequest() {
        locationRepository.startLocationRequest()
    }

    fun stopLocationRequest() {
        locationRepository.stopLocationRequest()
    }

}
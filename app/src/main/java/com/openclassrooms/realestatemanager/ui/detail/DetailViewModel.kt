package com.openclassrooms.realestatemanager.ui.detail

import android.app.Application
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.local.model.AgentEntity
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateActivity
import com.openclassrooms.realestatemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val application: Application,
    realEstateRepository: RealEstateRepository,
    private val currentRealEstateRepository: CurrentRealEstateRepository,
    agentRepository: AgentRepository,
) : ViewModel() {

    val viewActionSingleLiveEvent: SingleLiveEvent<Intent> = SingleLiveEvent()

    private val realEstateFlow = currentRealEstateRepository.getCurrentRealEstateIdStateFlow()
        .filterNotNull()
        .flatMapLatest { currentRealEstateId ->
            realEstateRepository.getRealEstateById(currentRealEstateId)
        }

    private val agentListFlow = agentRepository.getAllAgents()

    val mediatorFlow: LiveData<DetailViewState> = liveData(Dispatchers.IO) {
        combine(
            realEstateFlow,
            agentListFlow,
        ) { realEstate, agentList ->

            val photoListItemViewStateList = realEstate.realEstatePhotoLists.map {
                RealEstatePhotoItemViewState.Content(
                    it.photoId,
                    it.url,
                    it.description
                )
            }

            val agentInCharge: AgentEntity? =
                agentList.find {
                    it.agentId == realEstate.realEstateEntity.agentIdInCharge
                }

            emit(
                DetailViewState(
                    photoListItemViewStateList,
                    realEstate.realEstateEntity.descriptionBody,
                    realEstate.realEstateEntity.squareMeter,
                    realEstate.realEstateEntity.numberOfRooms,
                    realEstate.realEstateEntity.numberOfBathrooms,
                    realEstate.realEstateEntity.numberOfBedrooms,
                    realEstate.realEstateEntity.address,
                    agentInCharge?.name ?: application.getString(R.string.none),
                    true
                )
            )

        }.collectLatest() {

        }
    }


    fun onToolBarMenuModifyClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            val realEstateId = currentRealEstateRepository.getCurrentRealEstateIdStateFlow().firstOrNull()
            withContext(Dispatchers.Main) {
                viewActionSingleLiveEvent.setValue(
                    AddOrModifyRealEstateActivity.navigate(
                        application,
                        realEstateId
                    )
                )
            }
        }

    }

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

//    fun startLocationRequest() {
//        locationRepository.startLocationRequest()
//    }
//
//    fun stopLocationRequest() {
//        locationRepository.stopLocationRequest()
//    }

}
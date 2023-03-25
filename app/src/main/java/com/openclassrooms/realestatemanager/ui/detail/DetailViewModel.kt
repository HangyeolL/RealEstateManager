package com.openclassrooms.realestatemanager.ui.detail

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.local.model.AgentEntity
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateActivity
import com.openclassrooms.realestatemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.file.Files.find
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    realEstateRepository: RealEstateRepository,
    agentRepository: AgentRepository,
) : ViewModel() {

    val detailViewStateLiveData: LiveData<DetailViewState> = liveData(Dispatchers.IO) {

        val realEstateFlow = savedStateHandle.get<Int>("realEstateId")
            ?.let {
                realEstateRepository.getRealEstateById(it)
            }

        val agentListFlow = agentRepository.getAllAgents()

        val photoListItemViewStateList = realEstateFlow?.collectLatest { realEstateWithPhotos ->
            realEstateWithPhotos.realEstatePhotoLists.map {
                RealEstatePhotoItemViewState.Content(
                    it.photoId,
                    it.url,
                    it.description
                )
            }
        }

        var agentInCharge: AgentEntity? = null

        agentListFlow.collectLatest { agentEntityList ->
            agentInCharge = agentEntityList.find {
                it.agentId == savedStateHandle.get<Int>("realEstateId")
            }
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
        val realEstateId =
            currentRealEstateRepository.getCurrentRealEstateIdStateFlow().firstOrNull()
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
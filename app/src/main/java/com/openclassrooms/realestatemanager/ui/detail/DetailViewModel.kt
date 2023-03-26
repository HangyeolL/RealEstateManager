package com.openclassrooms.realestatemanager.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.detail_map.DetailMapViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    realEstateRepository: RealEstateRepository,
    agentRepository: AgentRepository,
) : ViewModel() {

    private val realEstateId = savedStateHandle.get<Int>("realEstateId")

    val detailViewStateLiveData: LiveData<DetailViewState> = liveData(Dispatchers.IO) {

        if (realEstateId != null) {
            coroutineScope {
                val realEstateFlow =
                    async { realEstateRepository.getRealEstateById(realEstateId).first() }
                val realEstate = realEstateFlow.await()

                val agentListFlow = async { agentRepository.getAllAgents().first() }
                val agentList = agentListFlow.await()

                val photoListItemViewStateList =
                    realEstate.realEstatePhotoLists.map { realEstateWithPhoto ->
                        RealEstatePhotoItemViewState.Content(
                            realEstateWithPhoto.photoId,
                            realEstateWithPhoto.url,
                            realEstateWithPhoto.description
                        )
                    }

                val agentInChargeName = agentList.find { agent ->
                    agent.agentId == realEstate.realEstateEntity.agentIdInCharge
                }?.name

                emit(
                    DetailViewState(
                        photoListItemViewStateList,
                        realEstate.realEstateEntity.descriptionBody,
                        realEstate.realEstateEntity.squareMeter,
                        realEstate.realEstateEntity.numberOfRooms,
                        realEstate.realEstateEntity.numberOfBathrooms,
                        realEstate.realEstateEntity.numberOfBedrooms,
                        realEstate.realEstateEntity.address,
                        agentInChargeName ?: application.getString(R.string.none),
                        true
                    )
                )
            }
        } else {
            // TODO Supposed to not arrive here ! or invisible Ui ? empty
        }
    }

    val mapViewStateLiveData: LiveData<DetailMapViewState> = liveData(Dispatchers.IO) {
        realEstateId?.let {
            realEstateRepository.getRealEstateById(it).collectLatest { realEstateWithPhotosEntity ->
                emit(
                    DetailMapViewState(
                        realEstateWithPhotosEntity.realEstateEntity.latLng
                    )
                )
            }
        }
    }

}

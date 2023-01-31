package com.openclassrooms.realestatemanager.ui.realEstateList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

import javax.inject.Inject

@HiltViewModel
class RealEstateListViewModel @Inject constructor(
    private val agentRepository: AgentRepository,
    private val realEstateRepository: RealEstateRepository
) : ViewModel() {

    val realEstateListLiveData: LiveData<List<RealEstateListViewStateItem>> =
        liveData(Dispatchers.IO) {
            realEstateRepository.getAllRealEstates().collect() { realEstateEntities ->
                emit(realEstateEntities.map {
                    RealEstateListViewStateItem(
                        it.id,
                        1,
                        it.type,
                        it.city,
                        it.price
                    )
                })
            }
        }


}
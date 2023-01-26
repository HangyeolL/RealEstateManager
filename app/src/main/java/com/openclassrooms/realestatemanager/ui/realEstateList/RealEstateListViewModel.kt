package com.openclassrooms.realestatemanager.ui.realEstateList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class RealEstateListViewModel @Inject constructor(
    private val agentRepository: AgentRepository
    ) : ViewModel() {

        val liveData : LiveData<List<String>> = liveData(Dispatchers.IO) {
            agentRepository.getAllAgents().collect { agentEntities ->
                emit(agentEntities.map { it.name })
            }
        }
}
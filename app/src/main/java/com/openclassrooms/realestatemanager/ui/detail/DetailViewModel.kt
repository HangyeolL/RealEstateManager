package com.openclassrooms.realestatemanager.ui.detail

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val agentRepository: AgentRepository,
    private val currentRealEstateRepository: CurrentRealEstateRepository,
) : ViewModel() {

    private val allAgentsListFlow = agentRepository.getAllAgents()

    private val _detailViewState = MutableStateFlow<DetailViewState?>(null)
    val detailViewState = _detailViewState.asStateFlow()

    init {

    }




}
package com.openclassrooms.realestatemanager.ui.addRealEstate

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddRealEstateViewModel @Inject constructor(
    private val agentRepository: AgentRepository,

) : ViewModel() {


}
package com.openclassrooms.realestatemanager.ui.realEstateList

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.data.local.repository.AgentRepositoryImpl
import com.openclassrooms.realestatemanager.domain.repository.AgentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RealEstateListViewModel @Inject constructor(
    private val agentRepository: AgentRepository
    ) : ViewModel() {

}
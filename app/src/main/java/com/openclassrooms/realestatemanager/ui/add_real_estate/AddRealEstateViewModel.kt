package com.openclassrooms.realestatemanager.ui.add_real_estate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.ui.add_real_estate.AddRealEstateFragment.Companion.KEY_REAL_ESTATE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddRealEstateViewModel @Inject constructor(
    private val agentRepository: AgentRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    init {
        val realEstateId: Int? = savedStateHandle[KEY_REAL_ESTATE_ID]

        if (realEstateId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                // TODO Hangyeol Query RealEstateRepository to fill the view fields
            }
        }
    }
}
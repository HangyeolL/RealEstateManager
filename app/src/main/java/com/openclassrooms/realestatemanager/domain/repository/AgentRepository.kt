package com.openclassrooms.realestatemanager.domain.repository

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.data.model.AgentEntity

interface AgentRepository {
    suspend fun upsertAgent(agent: AgentEntity)

    fun getAllAgents(): LiveData<List<AgentEntity>>

    suspend fun deleteAgent(agent: AgentEntity)

}
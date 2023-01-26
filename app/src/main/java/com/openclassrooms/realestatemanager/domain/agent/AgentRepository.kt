package com.openclassrooms.realestatemanager.domain.agent

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.data.model.AgentEntity
import kotlinx.coroutines.flow.Flow

interface AgentRepository {

    suspend fun upsertAgent(agent: AgentEntity)

    fun getAllAgents(): Flow<List<AgentEntity>>

    suspend fun deleteAgent(agent: AgentEntity)

}
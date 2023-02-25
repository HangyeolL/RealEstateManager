package com.openclassrooms.realestatemanager.domain.agent

import com.openclassrooms.realestatemanager.data.local.model.AgentEntity
import kotlinx.coroutines.flow.Flow

interface AgentRepository {

    suspend fun upsertAgent(agent: AgentEntity)

    fun getAllAgents(): Flow<List<AgentEntity>>

    fun getAgentById(id: Int): Flow<AgentEntity>

    suspend fun deleteAgent(agent: AgentEntity)

}
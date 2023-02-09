package com.openclassrooms.realestatemanager.data.local.repository

import com.openclassrooms.realestatemanager.data.local.dao.AgentDao
import com.openclassrooms.realestatemanager.data.model.AgentEntity
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AgentRepositoryImpl @Inject constructor(private val agentDao: AgentDao) : AgentRepository {

    override suspend fun upsertAgent(agent: AgentEntity) {
        agentDao.upsertAgent(agent)
    }

    override fun getAllAgents(): Flow<List<AgentEntity>> = agentDao.getAllAgents()

    override fun getAgentById(id: Int): Flow<AgentEntity> = agentDao.getAgentById(id)

    override suspend fun deleteAgent(agent: AgentEntity) {
        agentDao.deleteAgent(agent)
    }
}
package com.openclassrooms.realestatemanager.data.local.repository

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.data.local.dao.AgentDao
import com.openclassrooms.realestatemanager.data.model.AgentEntity
import com.openclassrooms.realestatemanager.domain.repository.AgentRepository

class AgentRepositoryImpl(private val agentDao: AgentDao): AgentRepository {

    override suspend fun upsertAgent(agent: AgentEntity) {
        agentDao.upsertAgent(agent)
    }

    override fun getAllAgents(): LiveData<List<AgentEntity>> = agentDao.getAllAgents()

    override suspend fun deleteAgent(agent: AgentEntity) {
        agentDao.deleteAgent(agent)
    }
}
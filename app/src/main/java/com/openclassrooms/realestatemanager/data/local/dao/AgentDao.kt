package com.openclassrooms.realestatemanager.data.local.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.data.model.AgentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAgent(agent: AgentEntity)

    @Query("SELECT * FROM agents")
    fun getAllAgents(): Flow<List<AgentEntity>>

    @Query("SELECT * FROM agents WHERE agentId = :agentId")
    fun getAgentById(agentId: Int) : Flow<AgentEntity>

    @Delete
    suspend fun deleteAgent(agent: AgentEntity)
}

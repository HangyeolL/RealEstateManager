package com.openclassrooms.realestatemanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.data.model.AgentEntity

@Dao
interface AgentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAgent(agent: AgentEntity)

    @Query("SELECT * FROM agents")
    fun getAllAgents(): LiveData<List<AgentEntity>>

    @Delete
    suspend fun deleteAgent(agent: AgentEntity)
}

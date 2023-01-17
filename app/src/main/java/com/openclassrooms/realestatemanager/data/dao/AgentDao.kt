package com.openclassrooms.realestatemanager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.data.Agent

@Dao
interface AgentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAgent(agent: Agent)

    @Query("SELECT * FROM agents")
    fun getAllAgents(): LiveData<List<Agent>>

    @Delete
    suspend fun deleteAgent(agent: Agent)
}

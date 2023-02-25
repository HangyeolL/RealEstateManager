package com.openclassrooms.realestatemanager.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agents")
data class AgentEntity(

    @PrimaryKey(autoGenerate = true)
    val agentId: Int = 0,
    val name: String,
    val email: String,
    val photoUrl: String
)

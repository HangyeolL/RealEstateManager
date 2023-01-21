package com.openclassrooms.realestatemanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "agents"
)
data class AgentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val photoUrl: String
)

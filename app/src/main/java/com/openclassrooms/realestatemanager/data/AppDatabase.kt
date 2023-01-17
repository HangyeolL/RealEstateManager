package com.openclassrooms.realestatemanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.data.dao.AgentDao
import com.openclassrooms.realestatemanager.data.dao.RealEstateDao

@Database(
    entities = [Agent::class, RealEstate::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAgentDao(): AgentDao
    abstract fun getRealEstateDao(): RealEstateDao


}
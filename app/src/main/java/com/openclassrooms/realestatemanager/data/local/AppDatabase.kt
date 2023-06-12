package com.openclassrooms.realestatemanager.data.local

import androidx.room.*
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.data.local.dao.AgentDao
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.local.model.AgentEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstatePhotoEntity

@Database(
    entities = [AgentEntity::class, RealEstateEntity::class, RealEstatePhotoEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAgentDao(): AgentDao
    abstract fun getRealEstateDao(): RealEstateDao

    companion object {
        const val DATABASE_NAME = "RealEstateManager_database"
    }

}

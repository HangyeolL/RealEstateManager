package com.openclassrooms.realestatemanager.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.WorkManager
import com.openclassrooms.realestatemanager.data.local.dao.AgentDao
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.model.AgentEntity
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity

@Database(
    entities = [AgentEntity::class, RealEstateEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAgentDao(): AgentDao
    abstract fun getRealEstateDao(): RealEstateDao

    companion object {
        private const val DATABASE_NAME = "RealEstateManager_database"

        fun create(
            application: Application,
            workManager: WorkManager
        ): AppDatabase {
            val builder = Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)

            builder.addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {

                }
             })

            return builder.build()
        }
    }

}
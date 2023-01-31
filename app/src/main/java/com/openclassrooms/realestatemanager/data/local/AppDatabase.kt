package com.openclassrooms.realestatemanager.data.local

import android.app.Application
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.openclassrooms.realestatemanager.data.local.dao.AgentDao
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.model.AgentEntity
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity

@Database(
    entities = [AgentEntity::class, RealEstateEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    // TODO how to chain work to insert AgentEntities with WorkManager

    abstract fun getAgentDao(): AgentDao
    abstract fun getRealEstateDao(): RealEstateDao

    companion object {
        private const val DATABASE_NAME = "RealEstateManager_database"

        fun create(
            application: Application,
            workManager: WorkManager,
            gson: Gson
        ): AppDatabase {
            val builder = Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)

            builder.addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    Log.d("ROOM", "onCreate callback called")
                    val agentEntitiesAsJson = gson.toJson(
                        listOf(
                            AgentEntity(1, "Agent Jake", "Jake@email.com", "abc"),
                            AgentEntity(2, "Agent Smith", "Smith@email.com", "abc"),
                            AgentEntity(3, "Agent Mike", "Mike@email.com", "abc"),
                            AgentEntity(3, "Agent Ken", "Ken@email.com", "abc"),
                        )
                    )

                    val realEstateEntitiesAsJson = gson.toJson(
                        listOf(
                            RealEstateEntity(
                                type = "Studio",
                                descriptionBody = "very nice studio in city center",
                                squareMeter = 35,
                                city = "Paris",
                                price = 245000,
                                numberOfRooms = 1,
                                numberOfBathrooms = 1,
                                numberOfBedrooms = 1,
                                address = "1 road to happiness",
                                garage = false,
                                guard = true,
                                garden = false,
                                elevator = true,
                                groceryStoreNearby = true,
                                isSoldOut = false,
                                dataOfSold = null,
                                marketSince = "01/01/2022",
                                agentIdInCharge = 1,
                                latLng = LatLng(22.22, 22.22)
                            ),
                            RealEstateEntity(
                                type = "Apartment",
                                descriptionBody = "very nice Apartment in city center",
                                squareMeter = 55,
                                city = "Paris",
                                price = 425000,
                                numberOfRooms = 1,
                                numberOfBathrooms = 1,
                                numberOfBedrooms = 2,
                                address = "2 road to happiness",
                                garage = true,
                                guard = true,
                                garden = false,
                                elevator = true,
                                groceryStoreNearby = true,
                                isSoldOut = false,
                                dataOfSold = null,
                                marketSince = "01/01/2022",
                                agentIdInCharge = 1,
                                latLng = LatLng(22.22, 22.22)
                            ),
                            RealEstateEntity(
                                type = "Apartment",
                                descriptionBody = "very nice Apartment not too far from bus stop and subway",
                                squareMeter = 90,
                                city = "Paris",
                                price = 465000,
                                numberOfRooms = 2,
                                numberOfBathrooms = 1,
                                numberOfBedrooms = 2,
                                address = "3 road to happiness",
                                garage = true,
                                guard = true,
                                garden = true,
                                elevator = true,
                                groceryStoreNearby = true,
                                isSoldOut = false,
                                dataOfSold = null,
                                marketSince = "01/01/2022",
                                agentIdInCharge = 1,
                                latLng = LatLng(22.22, 22.22)
                            ),
                            RealEstateEntity(
                                type = "Apartment",
                                descriptionBody = "very nice house with tranquility",
                                squareMeter = 110,
                                city = "Paris",
                                price = 570000,
                                numberOfRooms = 2,
                                numberOfBathrooms = 2,
                                numberOfBedrooms = 4,
                                address = "4 road to happiness",
                                garage = true,
                                guard = true,
                                garden = true,
                                elevator = true,
                                groceryStoreNearby = true,
                                isSoldOut = false,
                                dataOfSold = null,
                                marketSince = "01/01/2022",
                                agentIdInCharge = 1,
                                latLng = LatLng(22.22, 22.22)
                            ),
                            RealEstateEntity(
                                type = "Studio",
                                descriptionBody = "very nice studio next to grocery store",
                                squareMeter = 19,
                                city = "Paris",
                                price = 190000,
                                numberOfRooms = 1,
                                numberOfBathrooms = 1,
                                numberOfBedrooms = 1,
                                address = "5 road to happiness",
                                garage = false,
                                guard = false,
                                garden = false,
                                elevator = false,
                                groceryStoreNearby = true,
                                isSoldOut = false,
                                dataOfSold = null,
                                marketSince = "01/01/2022",
                                agentIdInCharge = 1,
                                latLng = LatLng(22.22, 22.22)
                            ),
                            RealEstateEntity(
                                type = "Studio",
                                descriptionBody = "suitable for students",
                                squareMeter = 22,
                                city = "Paris",
                                price = 220000,
                                numberOfRooms = 1,
                                numberOfBathrooms = 1,
                                numberOfBedrooms = 1,
                                address = "6 road to happiness",
                                garage = false,
                                guard = false,
                                garden = false,
                                elevator = false,
                                groceryStoreNearby = true,
                                isSoldOut = false,
                                dataOfSold = null,
                                marketSince = "01/01/2022",
                                agentIdInCharge = 1,
                                latLng = LatLng(22.22, 22.22)
                            ),
                            RealEstateEntity(
                                type = "Studio",
                                descriptionBody = "suitable for students",
                                squareMeter = 7,
                                city = "Paris",
                                price = 120000,
                                numberOfRooms = 1,
                                numberOfBathrooms = 1,
                                numberOfBedrooms = 1,
                                address = "7 road to happiness",
                                garage = false,
                                guard = false,
                                garden = false,
                                elevator = false,
                                groceryStoreNearby = true,
                                isSoldOut = false,
                                dataOfSold = null,
                                marketSince = "01/01/2022",
                                agentIdInCharge = 1,
                                latLng = LatLng(22.22, 22.22)
                            ),
                            RealEstateEntity(
                                type = "House",
                                descriptionBody = "Cozy house not too far from city center",
                                squareMeter = 60,
                                city = "Paris",
                                price = 415000,
                                numberOfRooms = 2,
                                numberOfBathrooms = 1,
                                numberOfBedrooms = 1,
                                address = "8 road to happiness",
                                garage = true,
                                guard = false,
                                garden = true,
                                elevator = false,
                                groceryStoreNearby = true,
                                isSoldOut = false,
                                dataOfSold = null,
                                marketSince = "01/01/2022",
                                agentIdInCharge = 1,
                                latLng = LatLng(22.22, 22.22)
                            ),
                            RealEstateEntity(
                                type = "House",
                                descriptionBody = "In the middle of city center. you have everything that you need",
                                squareMeter = 150,
                                city = "Paris",
                                price = 699000,
                                numberOfRooms = 2,
                                numberOfBathrooms = 2,
                                numberOfBedrooms = 3,
                                address = "9 road to happiness",
                                garage = true,
                                guard = false,
                                garden = true,
                                elevator = false,
                                groceryStoreNearby = true,
                                isSoldOut = false,
                                dataOfSold = null,
                                marketSince = "01/01/2022",
                                agentIdInCharge = 1,
                                latLng = LatLng(22.22, 22.22)
                            ),
                            RealEstateEntity(
                                type = "House",
                                descriptionBody = "little wooden house ! close to nature, not too far from the forest",
                                squareMeter = 55,
                                city = "Paris",
                                price = 599000,
                                numberOfRooms = 3,
                                numberOfBathrooms = 1,
                                numberOfBedrooms = 2,
                                address = "10 road to happiness",
                                garage = true,
                                guard = false,
                                garden = true,
                                elevator = false,
                                groceryStoreNearby = true,
                                isSoldOut = false,
                                dataOfSold = null,
                                marketSince = "01/01/2022",
                                agentIdInCharge = 1,
                                latLng = LatLng(22.22, 22.22)
                            ),
                        )
                    )

                    workManager.enqueue(
                        OneTimeWorkRequestBuilder<DatabaseInitializationWorker>()
//                            .setInputData(workDataOf(DatabaseInitializationWorker.AGENT_ENTITIES_INPUT_DATA to agentEntitiesAsJson))
                            .setInputData(workDataOf(DatabaseInitializationWorker.REAL_ESTATE_ENTITIES_INPUT_DATA to realEstateEntitiesAsJson))
                            .build()
                    )
                }
            })

            return builder.build()
        }

    }


}
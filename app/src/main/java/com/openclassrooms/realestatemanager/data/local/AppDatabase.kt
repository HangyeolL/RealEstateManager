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
import com.openclassrooms.realestatemanager.data.model.RealEstatePhoto
import com.openclassrooms.realestatemanager.data.model.RealEstateWithPhotos

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

                    val agentEntitiesAsJson = gson.toJson(getAgentList())
                    val realEstateWithPhotosAsJson = gson.toJson(getRealEstatesWithPhotos())

                    workManager.enqueue(
                        OneTimeWorkRequestBuilder<DatabaseInitializationWorker>()
                            .setInputData(
                                workDataOf(
                                    DatabaseInitializationWorker.REAL_ESTATE_ENTITIES_INPUT_DATA to realEstateWithPhotosAsJson,
                                    DatabaseInitializationWorker.AGENT_ENTITIES_INPUT_DATA to agentEntitiesAsJson
                                )
                            )
                            .build()
                    )
                }


            })

            return builder.build()
        }

        private fun getAgentList(): List<AgentEntity> =
            listOf(
                AgentEntity(
                    name = "Agent Jake",
                    email = "Jake@email.com",
                    photoUrl = "abc"
                ),
                AgentEntity(
                    name = "Agent Mike",
                    email = "Mike@email.com",
                    photoUrl = "abc"
                ),
                AgentEntity(
                    name = "Agent Smith",
                    email = "Smith@email.com",
                    photoUrl = "abc"
                ),
                AgentEntity(
                    name = "Agent Jhon",
                    email = "Jhon@email.com",
                    photoUrl = "abc"
                ),
                AgentEntity(
                    name = "Agent Charly",
                    email = "Charly@email.com",
                    photoUrl = "abc"
                ),
            )

        private fun getRealEstatesWithPhotos(): List<RealEstateWithPhotos> =
            listOf(
                RealEstateWithPhotos(
                    RealEstateEntity(
                        type = "Studio",
                        descriptionBody = "very nice studio in city center",
                        squareMeter = 35,
                        city = "Paris",
                        price = 245000,
                        numberOfRooms = 2,
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
                    getRealEstatePhotoListOfId1()
                ),
                RealEstateWithPhotos(
                    RealEstateEntity(
                        type = "Apartment",
                        descriptionBody = "very nice Apartment in city center",
                        squareMeter = 55,
                        city = "Paris",
                        price = 425000,
                        numberOfRooms = 3,
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
                    getRealEstatePhotoListOfId2()
                ),
                RealEstateWithPhotos(
                    RealEstateEntity(
                        type = "Apartment",
                        descriptionBody = "very nice Apartment not too far from bus stop and subway",
                        squareMeter = 90,
                        city = "Paris",
                        price = 465000,
                        numberOfRooms = 3,
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
                    getRealEstatePhotoListOfId3()
                ),
                RealEstateWithPhotos(
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
                    emptyList()

                ),
                RealEstateWithPhotos(
                    RealEstateEntity(
                        type = "Studio",
                        descriptionBody = "very nice studio next to grocery store",
                        squareMeter = 19,
                        city = "Paris",
                        price = 190000,
                        numberOfRooms = 2,
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
                    emptyList()

                ),
                RealEstateWithPhotos(
                    RealEstateEntity(
                        type = "Studio",
                        descriptionBody = "suitable for students",
                        squareMeter = 22,
                        city = "Paris",
                        price = 220000,
                        numberOfRooms = 2,
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
                    emptyList()
                ),
                RealEstateWithPhotos(
                    RealEstateEntity(
                        type = "Studio",
                        descriptionBody = "suitable for students",
                        squareMeter = 7,
                        city = "Paris",
                        price = 120000,
                        numberOfRooms = 2,
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
                    emptyList()

                ),
                RealEstateWithPhotos(
                    RealEstateEntity(
                        type = "House",
                        descriptionBody = "In the middle of city center. you have everything that you need",
                        squareMeter = 150,
                        city = "Paris",
                        price = 699000,
                        numberOfRooms = 5,
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
                    emptyList()

                ),
                RealEstateWithPhotos(
                    RealEstateEntity(
                        type = "Apartment",
                        descriptionBody = "very nice house with tranquility",
                        squareMeter = 110,
                        city = "Paris",
                        price = 570000,
                        numberOfRooms = 6,
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
                    emptyList()
                ),
                RealEstateWithPhotos(
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
                    emptyList()
                ),
            )

        private fun getRealEstatePhotoListOfId1(): List<RealEstatePhoto> =
            listOf(
                RealEstatePhoto(
                    1,
                    "data:image/jpeg;base64,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"
                ),
                RealEstatePhoto(
                    1,
                    ""
                ),
                RealEstatePhoto(
                    1,
                    ""
                ),
            )

        private fun getRealEstatePhotoListOfId2(): List<RealEstatePhoto> =
            listOf(
                RealEstatePhoto(
                    2,
                    "data:image/jpeg;base64,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"
                ),
                RealEstatePhoto(
                    2,
                    ""
                ),
                RealEstatePhoto(
                    2,
                    ""
                ),
            )

        private fun getRealEstatePhotoListOfId3(): List<RealEstatePhoto> =
            listOf(
                RealEstatePhoto(
                    3,
                    "data:image/jpeg;base64,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"
                ),
                RealEstatePhoto(
                    3,
                    ""
                ),
                RealEstatePhoto(
                    3,
                    ""
                ),
            )

        private fun getRealEstatePhotoListOfId4(): List<RealEstatePhoto> =
            listOf(
                RealEstatePhoto(
                    4,
                    "data:image/jpeg;base64,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"
                ),
                RealEstatePhoto(
                    4,
                    ""
                ),
                RealEstatePhoto(
                    4,
                    ""
                ),
            )

        private fun getRealEstatePhotoListOfId5(): List<RealEstatePhoto> =
            listOf(
                RealEstatePhoto(
                    5,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyw3BJG0EPlz8xqCgcXY-bLJ76Zu1u3sFGOA&usqp=CAU"
                ),
                RealEstatePhoto(
                    5,
                    ""
                ),
                RealEstatePhoto(
                    5,
                    ""
                ),
            )
    }
}
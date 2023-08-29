package com.openclassrooms.realestatemanager.data

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.work.WorkManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.openclassrooms.realestatemanager.data.local.AppDatabase
import com.openclassrooms.realestatemanager.data.local.AppDatabase.Companion.DATABASE_NAME
import com.openclassrooms.realestatemanager.data.local.dao.AgentDao
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.remote.MyGoogleApi
import com.openclassrooms.realestatemanager.data.remote.MyGoogleApiHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataProvideModule {

    @Provides
    @Singleton
    fun provideWorkManager(application: Application): WorkManager = WorkManager.getInstance(application)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .createFromAsset("database/RealEstateManager_database.db")
            .build()

    @Provides
    @Singleton
    fun provideAgentDao(appDatabase: AppDatabase): AgentDao = appDatabase.getAgentDao()

    @Provides
    @Singleton
    fun provideRealEstateDao(appDatabase: AppDatabase): RealEstateDao =
        appDatabase.getRealEstateDao()

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun provideMyGoogleApi(): MyGoogleApi =
        MyGoogleApiHolder.getInstance().create(MyGoogleApi::class.java)

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideDataStore(application: Application): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { application.preferencesDataStoreFile("settings") }
        )

}
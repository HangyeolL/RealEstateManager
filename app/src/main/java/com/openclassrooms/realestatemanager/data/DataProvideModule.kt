package com.openclassrooms.realestatemanager.data

import android.app.Application
import androidx.work.WorkManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.openclassrooms.realestatemanager.data.local.AppDatabase
import com.openclassrooms.realestatemanager.data.local.dao.AgentDao
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideAppDatabase(
        application: Application,
        workManager: WorkManager,
        gson: Gson
    ): AppDatabase = AppDatabase.create(application, workManager, gson)

    @Provides
    @Singleton
    fun provideAgentDao(appDatabase: AppDatabase): AgentDao = appDatabase.getAgentDao()

    @Provides
    @Singleton
    fun provideRealEstateDao(appDatabase: AppDatabase): RealEstateDao = appDatabase.getRealEstateDao()

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)






}
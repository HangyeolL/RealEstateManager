package com.openclassrooms.realestatemanager.di

import android.app.Application
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
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application) : AppDatabase = AppDatabase.create(application)

    @Provides
    @Singleton
    fun provideAgentDao(appDatabase: AppDatabase): AgentDao = appDatabase.getAgentDao()

    @Provides
    @Singleton
    fun provideRealEstateDao(appDatabase: AppDatabase): RealEstateDao = appDatabase.getRealEstateDao()
}
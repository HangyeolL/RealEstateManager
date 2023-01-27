package com.openclassrooms.realestatemanager.data

import com.openclassrooms.realestatemanager.data.local.repository.AgentRepositoryImpl
import com.openclassrooms.realestatemanager.data.local.repository.RealEstateRepositoryImpl
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindingModule {

    @Binds
    @Singleton
    abstract fun bindAgentRepository(impl: AgentRepositoryImpl): AgentRepository

    @Binds
    @Singleton
    abstract fun bindRealEstateRepository(impl: RealEstateRepositoryImpl): RealEstateRepository
}
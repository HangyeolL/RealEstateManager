package com.openclassrooms.realestatemanager.data

import com.openclassrooms.realestatemanager.data.local.repository.*
import com.openclassrooms.realestatemanager.data.remote.repository.AutocompleteRepositoryImpl
import com.openclassrooms.realestatemanager.data.remote.repository.GeocodingRepositoryImpl
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.domain.geocoding.GeocodingRepository
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.domain.search_criteria.SearchCriteriaRepository
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

    @Binds
    @Singleton
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl) : LocationRepository

    @Binds
    @Singleton
    abstract fun bindAutocompleteRepository(impl: AutocompleteRepositoryImpl): AutocompleteRepository

    @Binds
    @Singleton
    abstract fun bindGeocodingRepository(impl: GeocodingRepositoryImpl): GeocodingRepository

    @Binds
    @Singleton
    abstract fun currentRealEstateRepository(impl: CurrentRealEstateRepositoryImpl): CurrentRealEstateRepository

    @Binds
    @Singleton
    abstract fun searchCriteriaRepository(impl: SearchCriteriaRepositoryImpl): SearchCriteriaRepository

}
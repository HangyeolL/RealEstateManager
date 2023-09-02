package com.openclassrooms.realestatemanager.provider

import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ContentProviderEntryPoint {

    fun getRealEstateDao() : RealEstateDao
}
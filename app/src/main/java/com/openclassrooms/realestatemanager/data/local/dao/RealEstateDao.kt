package com.openclassrooms.realestatemanager.data.local.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstatePhotoEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstateWithPhotos
import kotlinx.coroutines.flow.Flow

@Dao
interface RealEstateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRealEstate(realEstateEntity: RealEstateEntity)

    @Insert
    suspend fun insertRealEstatePhoto(realEstatePhotoEntity: RealEstatePhotoEntity)

    @Query("DELETE FROM realEstates WHERE realEstateId = :realEstateId")
    suspend fun deleteRealEstate(realEstateId: Int)

    @Delete
    fun deleteRealEstatePhoto(realEstatePhotoEntity: RealEstatePhotoEntity)

    @Query("SELECT * FROM realEstates WHERE realEstateId = :realEstateId")
    fun getRealEstateById(realEstateId: Int) : Flow<RealEstateWithPhotos>

    @Transaction
    @Query("SELECT * FROM realEstates")
    fun getRealEstatesWithPhotos(): Flow<List<RealEstateWithPhotos>>


}
package com.openclassrooms.realestatemanager.data.local.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RealEstateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRealEstate(realEstate: RealEstateEntity)

    @Query("SELECT * FROM realEstates")
    fun getAllRealEstates(): Flow<List<RealEstateEntity>>

    @Query("SELECT * FROM realEstates WHERE id=realEstateId")
     fun getRealEstateById(id: Int) : Flow<RealEstateEntity>

    @Query("DELETE FROM realEstates WHERE id=:realEstateId")
    suspend fun deleteRealEstate(realEstateId: Int): Int

}
package com.openclassrooms.realestatemanager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.data.RealEstate

@Dao
interface RealEstateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRealEstate(realEstate: RealEstate)

    @Query("SELECT * FROM realEstates")
    fun getAllRealEstates(): LiveData<List<RealEstate>>

    @Query("DELETE FROM realEstates WHERE id=:realEstateId")
    suspend fun deleteRealEstate(realEstateId: Int): Int

}
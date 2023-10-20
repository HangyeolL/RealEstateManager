package com.openclassrooms.realestatemanager.provider

import android.content.ContentResolver
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import com.openclassrooms.realestatemanager.data.local.AppDatabase
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.provider.RealEstateContentProvider.Companion.CONTENT_URI
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class RealEstateContentProviderTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var appDatabase: AppDatabase

    private lateinit var contentResolver: ContentResolver

    @Before
    fun init() {
        hiltRule.inject()
        contentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @After
    fun close() {
        appDatabase.getRealEstateDao().nukeRealEstatePhotosTable()
        appDatabase.getRealEstateDao().nukeRealEstateTable()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_that_content_provider_gets_data() {
        runTest {
            appDatabase.getRealEstateDao().insertRealEstateNoReturn(getDefaultRealEstateEntity(1))
            advanceUntilIdle()
        }
        val cursor = contentResolver.query(CONTENT_URI, null, null, null)

        Log.e("test", "test_that_content_provider_gets_data: ${cursor.toString()}")

        assertThat(cursor).isNotNull()

        assertThat(cursor!!.count).isEqualTo(1)

        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("address"))).contains(
            getDefaultRealEstateEntity(1).address
        )
    }

    private fun getDefaultRealEstateEntity(realEstateId: Int) = RealEstateEntity(
        realEstateId = realEstateId,
        type = "type$realEstateId",
        descriptionBody = "description$realEstateId",
        squareMeter = realEstateId * 10,
        city = "city$realEstateId",
        price = realEstateId * 1000,
        numberOfBedrooms = realEstateId + 1,
        numberOfBathrooms = realEstateId + 1,
        numberOfRooms = (realEstateId + 1) * 2,
        address = "address$realEstateId",
        garage = false,
        guard = false,
        garden = false,
        elevator = false,
        groceryStoreNearby = false,
        isSoldOut = false,
        dateOfSold = null,
        marketSince = "0$realEstateId/06/2023",
        agentIdInCharge = realEstateId,
        latLng = LatLng(realEstateId.toDouble(), realEstateId.toDouble())
    )

}
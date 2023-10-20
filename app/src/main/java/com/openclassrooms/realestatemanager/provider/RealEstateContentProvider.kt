package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.RoomMasterTable.TABLE_NAME
import com.openclassrooms.realestatemanager.domain.realestate.RealEstateRepository
import dagger.hilt.EntryPoints
import javax.inject.Inject

class RealEstateContentProvider @Inject constructor(): ContentProvider() {

    companion object{
        const val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
        private const val REAL_ESTATE_TABLE = "realEstate_table"
        val CONTENT_URI : Uri = Uri.parse("content://$AUTHORITY/$REAL_ESTATE_TABLE")
    }

    lateinit var realEstateRepository: RealEstateRepository

    private val sURIMatcher = UriMatcher(UriMatcher.NO_MATCH)

    private val realEstate = 1
    private val realEstateID = 2

    init {
        sURIMatcher.addURI(AUTHORITY, REAL_ESTATE_TABLE, realEstate)
        sURIMatcher.addURI(AUTHORITY, "$REAL_ESTATE_TABLE/#",realEstateID)
    }

    override fun onCreate(): Boolean {
        realEstateRepository = EntryPoints.get(context!!, ContentProviderEntryPoint::class.java).realEstateRepository
        return true
    }

    override fun query(
        uri: Uri,
        strings: Array<String>?,
        s: String?,
        strings1: Array<String>?,
        s1: String?
    ): Cursor {
        return if (context != null) {
            val cursor = realEstateRepository.getAllRealEstatesAsCursor()
            cursor.setNotificationUri(context!!.contentResolver, uri)
            cursor
        } else {
            throw IllegalArgumentException("Failed to query row for uri $uri")
        }
    }

    override fun getType(uri: Uri): String {
        return "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        s: String?,
        strings: Array<String>?
    ): Int {
        return 0
    }
}
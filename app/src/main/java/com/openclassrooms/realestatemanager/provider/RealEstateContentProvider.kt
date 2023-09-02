package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import dagger.hilt.android.EntryPointAccessors

class RealEstateContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
        const val PATH_REAL_ESTATE_LIST = "REAL_ESTATE_LIST"

        val CONTENT_URI_1 = Uri.parse("content://$AUTHORITY/$PATH_REAL_ESTATE_LIST")

        const val CODE_REAL_ESTATE_LIST = 1

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PATH_REAL_ESTATE_LIST, CODE_REAL_ESTATE_LIST)
        }
    }

    private lateinit var realEstateDao: RealEstateDao

    override fun onCreate(): Boolean {
        val appContext = context?.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint = EntryPointAccessors.fromApplication(appContext, ContentProviderEntryPoint::class.java)
        realEstateDao = hiltEntryPoint.getRealEstateDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = when(uriMatcher.match(uri)) {
        CODE_REAL_ESTATE_LIST -> realEstateDao.getAllRealEstatesAsCursor()
        else -> {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }.apply {
        setNotificationUri(context?.contentResolver, uri)
    }

    override fun getType(p0: Uri): String? = null

    override fun insert(p0: Uri, p1: ContentValues?): Uri? = null

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int = 0

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int = 0


}
package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.RoomMasterTable.TABLE_NAME
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.domain.realestate.RealEstateRepository
import javax.inject.Inject


class RealEstateContentProvider @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
        const val PATH_REAL_ESTATE_LIST = "REAL_ESTATE_LIST"

        val CONTENT_URI_1 = Uri.parse("content://$AUTHORITY/$PATH_REAL_ESTATE_LIST")

        const val CODE_REAL_ESTATE_LIST = 1

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PATH_REAL_ESTATE_LIST, CODE_REAL_ESTATE_LIST)
        }
    }

    override fun onCreate(): Boolean {
        TODO("Not yet implemented")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }


}
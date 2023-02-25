package com.openclassrooms.realestatemanager.data.remote.repository

import com.google.android.gms.common.api.GoogleApi
import javax.inject.Inject

class AutoCompleteRepositoryImpl @Inject constructor(
    private val googleApi: GoogleApi,
) : AutoCompleteRepository {

}
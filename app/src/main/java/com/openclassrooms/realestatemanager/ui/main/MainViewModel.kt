package com.openclassrooms.realestatemanager.ui.main

import androidx.lifecycle.ViewModel
import androidx.work.*
import com.openclassrooms.realestatemanager.data.FirebaseSynchronizationWorker
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val workManager: WorkManager,
): ViewModel() {

    fun onUserOkClickedForDataSynchronization() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<FirebaseSynchronizationWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueue(syncRequest)
   }

    fun startLocationRequest() {
        locationRepository.startLocationRequest()
    }

    fun stopLocationRequest() {
        locationRepository.stopLocationRequest()
    }


}
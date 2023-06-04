package com.openclassrooms.realestatemanager.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.openclassrooms.realestatemanager.domain.firebase.FirebaseRepository
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

@HiltWorker
class DataSynchronizationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val realEstateRepository: RealEstateRepository,
    private val firebaseRepository: FirebaseRepository,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result =
        try {
            withContext(Dispatchers.IO) {
                val realEstateWithPhotosList = async {
                    realEstateRepository.getRealEstatesWithPhotos().first()
                }.await()

                firebaseRepository.setRealEstateWithPhotosList(realEstateWithPhotosList)

                Log.d("HL", "firebase synchronization success")
                Result.success()

            }

        } catch (e: Exception) {
            Log.d("HL", "${e.printStackTrace()}")
            Result.failure()
        }

}



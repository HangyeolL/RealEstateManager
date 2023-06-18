package com.openclassrooms.realestatemanager.data

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.firebase.FirebaseRepository
import com.openclassrooms.realestatemanager.domain.realestate.RealEstateRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

@HiltWorker
class FirebaseSynchronizationWorker @AssistedInject constructor(
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

                showNotification("Success")

                Result.success()
            }

        } catch (e: Exception) {
            Log.d("HL", "${e.printStackTrace()}")
            showNotification("Fail")
            Result.failure()
        }

    private fun showNotification(text: String) {
        // Create and configure the notification
        val notificationBuilder = NotificationCompat.Builder(applicationContext, "channel_id")
            .setContentTitle("Synchronization")
            .setContentText("$text to synchronize data with firebase !")
            .setSmallIcon(R.drawable.ic_baseline_sync_24)

        // Show the notification
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notificationBuilder.build())
    }

}



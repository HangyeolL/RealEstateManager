package com.openclassrooms.realestatemanager.data.local

import android.app.Application
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.openclassrooms.realestatemanager.domain.repository.AgentRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class DatabaseInitializeWorker @AssistedInject constructor(
    @Assisted application: Application,
    @Assisted workerParameters: WorkerParameters,
    agentRepository: AgentRepository
): CoroutineWorker(application, workerParameters) {

    override suspend fun doWork(): Result {

    }

}



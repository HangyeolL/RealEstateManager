package com.openclassrooms.realestatemanager.data.local

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.openclassrooms.realestatemanager.data.model.AgentEntity
import com.openclassrooms.realestatemanager.domain.repository.AgentRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class DatabaseInitializationWorker @AssistedInject constructor(
    @Assisted application: Application,
    @Assisted workerParameters: WorkerParameters,
    private val agentRepository: AgentRepository,
    private val gson: Gson
) : CoroutineWorker(application, workerParameters) {

    companion object {
        const val AGENT_ENTITIES_INPUT_DATA = "AGENT_ENTITIES_INPUT_DATA"
        const val REAL_ESTATE_ENTITIES_INPUT_DATA = "REAL_ESTATE_ENTITIES_INPUT_DATA"

    }

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            val agentEntitiesAsJson = inputData.getString(AGENT_ENTITIES_INPUT_DATA)

            if (agentEntitiesAsJson != null) {
                val agentEntities =
                    gson.fromJson<List<AgentEntity>>(agentEntitiesAsJson, AgentEntity::class.java)

                if (agentEntities != null) {
                    agentEntities.forEach { agentEntity ->
                        agentRepository.upsertAgent(agentEntity)
                    }
                    Result.success()
                } else {
                    Log.e(javaClass.simpleName, "Gson can't parse objects : $agentEntitiesAsJson")
                    Result.failure()
                }

            } else {
                return@withContext Result.failure()
            }
        }

}



package com.openclassrooms.realestatemanager.data.local

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.openclassrooms.realestatemanager.data.model.AgentEntity
import com.openclassrooms.realestatemanager.data.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.utils.fromJson
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class DatabaseInitializationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val agentRepository: AgentRepository,
    private val realEstateRepository: RealEstateRepository,
    private val gson: Gson,
//    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : CoroutineWorker(context, workerParameters) {

    companion object {
        const val AGENT_ENTITIES_INPUT_DATA = "AGENT_ENTITIES_INPUT_DATA"
        const val REAL_ESTATE_ENTITIES_INPUT_DATA = "REAL_ESTATE_ENTITIES_INPUT_DATA"
    }

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
//            val agentEntitiesAsJson = inputData.getString(AGENT_ENTITIES_INPUT_DATA)
//
//            if (agentEntitiesAsJson != null) {
//                val agentEntities =
//                    gson.fromJson<List<AgentEntity>>(agentEntitiesAsJson, AgentEntity::class.java)
//
//                if (agentEntities != null) {
//                    agentEntities.forEach { agentEntity ->
//                        agentRepository.upsertAgent(agentEntity)
//                    }
//                    Result.success()
//                } else {
//                    Log.e(javaClass.simpleName, "Gson can't parse objects : $agentEntitiesAsJson")
//                    Result.failure()
//                }
//            } else {
//                return@withContext Result.failure()
//            }

            val realEstateEntitiesAsJson = inputData.getString(REAL_ESTATE_ENTITIES_INPUT_DATA)

            if (realEstateEntitiesAsJson != null) {
                val realEstateEntities =
                    gson.fromJson<List<RealEstateEntity>>(json = realEstateEntitiesAsJson)

                if (realEstateEntities != null) {
                    realEstateEntities.forEach { realEstateEntity ->
                        realEstateRepository.upsertRealEstate(realEstateEntity)
                    }
                    Result.success()
                } else {
                    Log.e(javaClass.simpleName, "Gson can't parse objects : $realEstateEntitiesAsJson")
                    Result.failure()
                }

            } else {
                return@withContext Result.failure()
            }
        }

}



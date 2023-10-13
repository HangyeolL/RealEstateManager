package com.openclassrooms.realestatemanager.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.CoroutineDispatcherProvider
import com.openclassrooms.realestatemanager.domain.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    fun onRadioButtonDollarClicked(onFinished: () -> Unit) {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            dataStoreRepository.writeDollarBooleanToTrue()

            withContext(coroutineDispatcherProvider.main) {
                onFinished()
            }
        }
    }

    fun onRadioButtonEuroClicked(onFinished: () -> Unit) {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            dataStoreRepository.writeDollarBooleanToFalse()

            withContext(coroutineDispatcherProvider.main) {
                onFinished()
            }
        }
    }
}
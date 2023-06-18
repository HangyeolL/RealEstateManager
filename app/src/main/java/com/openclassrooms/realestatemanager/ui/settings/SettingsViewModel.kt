package com.openclassrooms.realestatemanager.ui.settings

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    fun onRadioButtonDollarClicked(onFinished: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.writeDollarBooleanToTrue()

            withContext(Dispatchers.Main) {
                onFinished()
            }
        }
    }

    fun onRadioButtonEuroClicked(onFinished: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.writeDollarBooleanToFalse()

            withContext(Dispatchers.Main) {
                onFinished()
            }
        }
    }

//    fun onRadioButtonEuroClicked(isChecked: Boolean, onFinished: () -> Unit) {
//        viewModelScope.launch(Dispatchers.IO) {
//            dataStoreRepository.writeEuroBoolean(isChecked)
//        }
//    }


}
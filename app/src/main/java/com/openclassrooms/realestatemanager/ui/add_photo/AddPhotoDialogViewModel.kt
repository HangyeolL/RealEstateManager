package com.openclassrooms.realestatemanager.ui.add_photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.local.model.RealEstatePhotoEntity
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPhotoDialogViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) : ViewModel() {

    fun onButtonOkClicked(realEstateId: Int?, picUriToString: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            realEstateRepository.insertRealEstatePhoto(
                RealEstatePhotoEntity(
                    realEstateIdOfPhoto = realEstateId,
                    url = picUriToString,
                    description = description,
                )
            )
        }

    }

}
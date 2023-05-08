package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstateListViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    private val currentRealEstateRepository: CurrentRealEstateRepository,
) : ViewModel() {

    var selectedRealEstateId: Int = currentRealEstateRepository.getCurrentRealEstateId().value

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currentRealEstateRepository.getCurrentRealEstateId().collectLatest { currentRealEstateId ->
                selectedRealEstateId = currentRealEstateId
                }
        }
    }

    val viewStateLiveData: LiveData<RealEstateListViewState> =
        liveData(Dispatchers.IO) {
            realEstateRepository.getRealEstatesWithPhotos().collectLatest { realEstateWithPhotosList ->

                    val itemViewStateList = ArrayList<RealEstateListItemViewState>()

                    realEstateWithPhotosList.forEach() { realEstateWithPhotos ->
                        itemViewStateList.add(
                            RealEstateListItemViewState(
                                realEstateWithPhotos.realEstateEntity.realEstateId,
                                if (realEstateWithPhotos.realEstatePhotoLists.isNotEmpty())
                                    realEstateWithPhotos.realEstatePhotoLists[0].url
                                else R.drawable.image_not_available.toString(),
                                realEstateWithPhotos.realEstateEntity.type,
                                realEstateWithPhotos.realEstateEntity.city,
                                realEstateWithPhotos.realEstateEntity.price,
                            )
                        )
                    }

                    emit(RealEstateListViewState(itemViewStateList))
                }

        }

    fun onRealEstateListItemClicked(itemId: Int) {
        Log.d("HG", "ListFragment viewModel onRealEstateListItemClicked called")
        currentRealEstateRepository.setCurrentRealEstateId(itemId)
    }
}
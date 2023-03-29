package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class RealEstateListViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
) : ViewModel() {

    val viewStateLiveData: LiveData<RealEstateListViewState> =
        liveData(Dispatchers.IO) {
            realEstateRepository.getRealEstatesWithPhotos()
                .collectLatest { realEstateWithPhotosList ->

                    val itemViewStateList = ArrayList<RealEstateListItemViewState>()

                    realEstateWithPhotosList.forEach() { realEstateWithPhotos ->
                        itemViewStateList.add(
                            RealEstateListItemViewState(
                                realEstateWithPhotos.realEstateEntity.realEstateId,
                                if (realEstateWithPhotos.realEstatePhotoLists.isNotEmpty())
                                    realEstateWithPhotos.realEstatePhotoLists[0].url
                                else
                                // TODO How to use drawable resource with Glide ! or is there a better way ?
                                    Uri.parse("android.resource://com.example.project/" + R.drawable.image_not_available)
                                        .toString(),
                                realEstateWithPhotos.realEstateEntity.type,
                                realEstateWithPhotos.realEstateEntity.city,
                                realEstateWithPhotos.realEstateEntity.price,
                            )
                        )
                    }

                    emit(RealEstateListViewState(itemViewStateList))
                }

        }

    fun onRealEstateItemClicked(it: Int) {

    }
}
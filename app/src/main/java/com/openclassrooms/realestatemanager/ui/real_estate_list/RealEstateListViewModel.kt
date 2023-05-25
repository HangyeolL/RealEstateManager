package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.domain.search_criteria.SearchCriteriaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstateListViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    private val currentRealEstateRepository: CurrentRealEstateRepository,
    private val searchCriteriaRepository: SearchCriteriaRepository,
) : ViewModel() {

    var selectedRealEstateId: Int = currentRealEstateRepository.getCurrentRealEstateId().value

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currentRealEstateRepository.getCurrentRealEstateId()
                .collectLatest { currentRealEstateId ->
                    selectedRealEstateId = currentRealEstateId
                }
        }
    }

    val viewStateLiveData: LiveData<RealEstateListViewState> =
        liveData(Dispatchers.IO) {
            realEstateRepository.getRealEstatesWithPhotos()
                .collectLatest { realEstateWithPhotosList ->
                    val filteredItemViewList = realEstateWithPhotosList.filter { realEstateWithPhotos ->
                        // Apply filtering based on search criteria
                        val searchCriteria = searchCriteriaRepository.getSearchCriteria().value
                        searchCriteria?.let { userSearchCriteria ->
                            var isMatch = true
                            // Apply filter conditions one by one
                            if (userSearchCriteria.elevator != realEstateWithPhotos.realEstateEntity.elevator) {
                                isMatch = false
                            }
                            if (userSearchCriteria.garden != realEstateWithPhotos.realEstateEntity.garden) {
                                isMatch = false
                            }
                            if (userSearchCriteria.garage != realEstateWithPhotos.realEstateEntity.garage) {
                                isMatch = false
                            }
                            // Add more filter conditions as needed
                            // Return true if all conditions match, otherwise false
                            isMatch
                        } ?: true // If search criteria is null, include all items
                    }.map { filteredRealEstateWithPhotos ->
                        RealEstateListItemViewState(
                            filteredRealEstateWithPhotos.realEstateEntity.realEstateId,
                            if (filteredRealEstateWithPhotos.realEstatePhotoLists.isNotEmpty())
                                filteredRealEstateWithPhotos.realEstatePhotoLists[0].url
                            else R.drawable.image_not_available.toString(),
                            filteredRealEstateWithPhotos.realEstateEntity.type,
                            filteredRealEstateWithPhotos.realEstateEntity.city,
                            filteredRealEstateWithPhotos.realEstateEntity.price,
                        )
                    }

                    emit(RealEstateListViewState(filteredItemViewList))

//                    val itemViewStateList = ArrayList<RealEstateListItemViewState>()
//
//                    realEstateWithPhotosList.forEach() { realEstateWithPhotos ->
//                        itemViewStateList.add(
//                            RealEstateListItemViewState(
//                                realEstateWithPhotos.realEstateEntity.realEstateId,
//                                if (realEstateWithPhotos.realEstatePhotoLists.isNotEmpty())
//                                    realEstateWithPhotos.realEstatePhotoLists[0].url
//                                else R.drawable.image_not_available.toString(),
//                                realEstateWithPhotos.realEstateEntity.type,
//                                realEstateWithPhotos.realEstateEntity.city,
//                                realEstateWithPhotos.realEstateEntity.price,
//                            )
//                        )
//                    }

//                    emit(RealEstateListViewState(itemViewStateList))
                }

        }

    fun onRealEstateListItemClicked(itemId: Int) {
        Log.d("HG", "ListFragment viewModel onRealEstateListItemClicked called")
        currentRealEstateRepository.setCurrentRealEstateId(itemId)
    }
}

package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import com.openclassrooms.realestatemanager.domain.search_criteria.SearchCriteriaRepository
import com.openclassrooms.realestatemanager.utils.MyUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstateListViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    private val currentRealEstateRepository: CurrentRealEstateRepository,
    private val searchCriteriaRepository: SearchCriteriaRepository,
) : ViewModel() {

    var selectedRealEstateId: Int = currentRealEstateRepository.getCurrentRealEstateId().value

    private val realEstatesWithPhotosFlow = realEstateRepository.getRealEstatesWithPhotos()
    private val searchCriteriaStateFlow = searchCriteriaRepository.getSearchCriteria()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currentRealEstateRepository.getCurrentRealEstateId()
                .collectLatest { currentRealEstateId ->
                    selectedRealEstateId = currentRealEstateId
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val viewStateLiveData: LiveData<RealEstateListViewState> =
        liveData(Dispatchers.IO) {
            combine(
                searchCriteriaStateFlow,
                realEstatesWithPhotosFlow,
            ) { searchCriteria, realEstatesWithPhotos ->

                val filteredItemViewList =
                    realEstatesWithPhotos.filter { realEstateWithPhotos ->
                        // Apply filtering based on search criteria
                        searchCriteria?.let { userSearchCriteria ->
                            val realEstateEntity = realEstateWithPhotos.realEstateEntity
                            var isMatch = true
                            // Apply filter conditions one by one
                            if (searchCriteria.type != null &&
                                searchCriteria.type != realEstateEntity.type
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.photoAvailable != null &&
                                realEstateWithPhotos.realEstatePhotoLists.isEmpty()
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.groceryStoreNearby != null &&
                                searchCriteria.groceryStoreNearby != realEstateEntity.groceryStoreNearby
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.guard != null &&
                                searchCriteria.guard != realEstateEntity.guard
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.garden != null &&
                                searchCriteria.garden != realEstateEntity.garden
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.garage != null &&
                                searchCriteria.garage != realEstateEntity.garage
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.elevator != null &&
                                searchCriteria.elevator != realEstateEntity.elevator
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.registeredRecently != null &&
                                MyUtils.compareDateAndGetTheDifference(realEstateEntity.marketSince) > 90
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.soldOutRecently != null &&
                                MyUtils.compareDateAndGetTheDifference(realEstateEntity.dateOfSold) > 90
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.minSquareMeter != null &&
                                realEstateEntity.squareMeter < searchCriteria.minSquareMeter
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.maxSquareMeter != null &&
                                realEstateEntity.squareMeter > searchCriteria.maxSquareMeter
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.minPrice != null &&
                                realEstateEntity.price < searchCriteria.minPrice
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.maxPrice != null &&
                                realEstateEntity.price > searchCriteria.maxPrice
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.numberOfBathrooms != null &&
                                realEstateEntity.numberOfBathrooms != searchCriteria.numberOfBathrooms
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.numberOfBedrooms != null &&
                                realEstateEntity.numberOfBedrooms != searchCriteria.numberOfBedrooms
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.agentIdInCharge != null &&
                                realEstateEntity.agentIdInCharge != searchCriteria.agentIdInCharge
                            ) {
                                isMatch = false
                            }
                            if (searchCriteria.city != null &&
                                realEstateEntity.city != searchCriteria.city
                            ) {
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
                            filteredRealEstateWithPhotos.realEstateEntity.isSoldOut
                        )
                    }

                emit(RealEstateListViewState(filteredItemViewList))

            }.collectLatest { }

        }

    fun onRealEstateListItemClicked(itemId: Int) {
        Log.d("HG", "ListFragment viewModel onRealEstateListItemClicked called")
        currentRealEstateRepository.setCurrentRealEstateId(itemId)
    }
}

package com.openclassrooms.realestatemanager.ui.real_estate_list

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.CoroutineDispatcherProvider
import com.openclassrooms.realestatemanager.domain.datastore.DataStoreRepository
import com.openclassrooms.realestatemanager.domain.realestate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realestate.RealEstateRepository
import com.openclassrooms.realestatemanager.domain.search_criteria.SearchCriteriaRepository
import com.openclassrooms.realestatemanager.utils.MyUtils
import com.openclassrooms.realestatemanager.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstateListViewModel @Inject constructor(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val context: Application,
    realEstateRepository: RealEstateRepository,
    private val currentRealEstateRepository: CurrentRealEstateRepository,
    searchCriteriaRepository: SearchCriteriaRepository,
    dataStoreRepository : DataStoreRepository,
) : ViewModel() {

    var selectedRealEstateId: Int = currentRealEstateRepository.getCurrentRealEstateId().value

    private val realEstatesWithPhotosFlow = realEstateRepository.getRealEstatesWithPhotos()
    private val searchCriteriaStateFlow = searchCriteriaRepository.getSearchCriteria()
    private val dollarBooleanFlow = dataStoreRepository.readDollarBoolean()

    init {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
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
                dollarBooleanFlow
            ) { searchCriteria, realEstatesWithPhotos, dollarBoolean,  ->

                val filteredItemViewList =
                    realEstatesWithPhotos.filter { realEstateWithPhotos ->
                        // Apply filtering based on search criteria
                        searchCriteria?.let { userSearchCriteria ->
                            val realEstateEntity = realEstateWithPhotos.realEstateEntity
                            var isMatch = true
                            // Apply filter conditions one by one
                            if (userSearchCriteria.type != null &&
                                userSearchCriteria.type != realEstateEntity.type
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.photoAvailable != null &&
                                realEstateWithPhotos.realEstatePhotoLists.isEmpty()
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.groceryStoreNearby != null &&
                                userSearchCriteria.groceryStoreNearby != realEstateEntity.groceryStoreNearby
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.guard != null &&
                                userSearchCriteria.guard != realEstateEntity.guard
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.garden != null &&
                                userSearchCriteria.garden != realEstateEntity.garden
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.garage != null &&
                                userSearchCriteria.garage != realEstateEntity.garage
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.elevator != null &&
                                userSearchCriteria.elevator != realEstateEntity.elevator
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.registeredRecently != null &&
                                MyUtils.compareDateAndGetTheDifference(realEstateEntity.marketSince) > 90
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.soldOutRecently != null &&
                                MyUtils.compareDateAndGetTheDifference(realEstateEntity.dateOfSold) > 90
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.minSquareMeter != null &&
                                realEstateEntity.squareMeter < userSearchCriteria.minSquareMeter
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.maxSquareMeter != null &&
                                realEstateEntity.squareMeter > userSearchCriteria.maxSquareMeter
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.minPrice != null &&
                                realEstateEntity.price < userSearchCriteria.minPrice
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.maxPrice != null &&
                                realEstateEntity.price > userSearchCriteria.maxPrice
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.numberOfBathrooms != null &&
                                realEstateEntity.numberOfBathrooms != userSearchCriteria.numberOfBathrooms
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.numberOfBedrooms != null &&
                                realEstateEntity.numberOfBedrooms != userSearchCriteria.numberOfBedrooms
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.agentIdInCharge != null &&
                                realEstateEntity.agentIdInCharge != userSearchCriteria.agentIdInCharge
                            ) {
                                isMatch = false
                            }
                            if (userSearchCriteria.city != null &&
                                realEstateEntity.city != userSearchCriteria.city
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
                            else
                                R.drawable.image_not_available.toString(),
                            filteredRealEstateWithPhotos.realEstateEntity.type,
                            filteredRealEstateWithPhotos.realEstateEntity.city,
                            if (dollarBoolean)
                                Utils.convertEuroToDollar(filteredRealEstateWithPhotos.realEstateEntity.price)
                            else
                                filteredRealEstateWithPhotos.realEstateEntity.price,
                            if (dollarBoolean)
                                context.getString(R.string.dollar_symbol_as_string)
                            else
                                context.getString(R.string.euro_symbol_as_string),
                            filteredRealEstateWithPhotos.realEstateEntity.isSoldOut,
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

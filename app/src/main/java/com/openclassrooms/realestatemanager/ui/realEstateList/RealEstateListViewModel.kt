package com.openclassrooms.realestatemanager.ui.realEstateList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.realEstate.CurrentRealEstateRepository
import com.openclassrooms.realestatemanager.domain.realEstate.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RealEstateListViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    private val currentRealEstateRepository: CurrentRealEstateRepository
) : ViewModel() {

//    val realEstateListLiveData: LiveData<List<RealEstateListItemViewState>> =
//        liveData(Dispatchers.IO) {
//            realEstateRepository.getAllRealEstates().collect() { realEstateEntities ->
//                emit(realEstateEntities.map {
//                    RealEstateListItemViewState(
//                        it.id,
//                        1,
//                        it.type,
//                        it.city,
//                        it.price
//                    )
//                })
//            }
//        }

    val viewStateLiveData: LiveData<RealEstateListViewState> =
        liveData(Dispatchers.IO) {
            realEstateRepository.getAllRealEstates().collect { realEstateList ->

                val itemViewStateList = ArrayList<RealEstateListItemViewState>()

                (realEstateList).forEach { realEstateEntity ->
                    itemViewStateList.add(RealEstateListItemViewState(
                        realEstateEntity.id,
                        1,
                        realEstateEntity.type,
                        realEstateEntity.city,
                        realEstateEntity.price
                    ))
                }

                emit(RealEstateListViewState(itemViewStateList))
            }

        }

    fun onRealEstateItemClicked(id: Int) {
        currentRealEstateRepository.setCurrentRealEstateId(id)
    }
}
package com.openclassrooms.realestatemanager.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.autocomplete.AutocompleteRepository
import com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate.AddOrModifyRealEstateTypeSpinnerItemViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.reflect.Constructor
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    application : Application,
    agentRepository: AgentRepository,
    private val autoCompleteRepository: AutocompleteRepository,
) : ViewModel() {

    val viewStateLiveData: LiveData<SearchViewState> =
        liveData(Dispatchers.IO) {

            val typeSpinnerItemViewStateList = listOf(
                AddOrModifyRealEstateTypeSpinnerItemViewState(
                    R.drawable.ic_baseline_house_24,
                    application.getString(R.string.house)
                ),
                AddOrModifyRealEstateTypeSpinnerItemViewState(
                    R.drawable.ic_baseline_apartment_24,
                    application.getString(R.string.apartment)
                ),
                AddOrModifyRealEstateTypeSpinnerItemViewState(
                    R.drawable.ic_baseline_bed_24,
                    application.getString(R.string.studio)
                )
            )

            emit(
                SearchViewState(
                    typeSpinnerItemViewStateList = typeSpinnerItemViewStateList,
                    agentSpinnerItemViewStateList = ,
                    city = ,
                    numberOfBathrooms = ,
                    numberOfBedrooms = ,
                    minSquareMeter = ,
                    maxSquareMeter = ,
                    minPrice = ,
                    maxPrice = ,
                    garage = ,
                    guard = ,
                    garden = ,
                    elevator = ,
                    groceryStoreNearby = ,
                    soldOutRecently = ,
                    registeredRecently = ,
                    photoAvailable = ,
                )
            )
        }
}
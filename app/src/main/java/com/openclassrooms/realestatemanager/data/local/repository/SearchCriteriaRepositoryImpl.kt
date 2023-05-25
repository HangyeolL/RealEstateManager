package com.openclassrooms.realestatemanager.data.local.repository

import android.util.Log
import com.openclassrooms.realestatemanager.domain.search_criteria.SearchCriteriaRepository
import com.openclassrooms.realestatemanager.domain.search_criteria.model.SearchCriteria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SearchCriteriaRepositoryImpl @Inject constructor(

) : SearchCriteriaRepository {

    private val searchCriteriaMutableStateFlow: MutableStateFlow<SearchCriteria?> = MutableStateFlow(null)
    private val searchCriteriaStateFlow: StateFlow<SearchCriteria?> = searchCriteriaMutableStateFlow.asStateFlow()

    override fun getSearchCriteria(): StateFlow<SearchCriteria?> = searchCriteriaStateFlow

    override fun setSearchCriteria(searchCriteria : SearchCriteria) {
        searchCriteriaMutableStateFlow.value = searchCriteria
        Log.d("HG", "SearchCriteriaRepository setting SearchCriteria to ${searchCriteriaStateFlow.value}")
    }
}
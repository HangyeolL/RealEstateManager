package com.openclassrooms.realestatemanager.domain.search_criteria

import com.openclassrooms.realestatemanager.domain.search_criteria.model.SearchCriteria
import kotlinx.coroutines.flow.StateFlow

interface SearchCriteriaRepository {

    fun getSearchCriteria(): StateFlow<SearchCriteria?>

    fun setSearchCriteria(searchCriteria: SearchCriteria)

    fun resetSearchCriteria()
}
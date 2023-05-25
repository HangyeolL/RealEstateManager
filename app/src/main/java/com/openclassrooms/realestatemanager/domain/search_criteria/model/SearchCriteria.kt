package com.openclassrooms.realestatemanager.domain.search_criteria.model

data class SearchCriteria(
    val numberOfBathrooms: Int?,
    val numberOfBedrooms: Int?,
    val minSquareMeter: Int?,
    val maxSquareMeter: Int?,
    val minPrice: Int?,
    val maxPrice: Int?,

    val garage: Boolean,
    val guard: Boolean,
    val garden: Boolean,
    val elevator: Boolean,
    val groceryStoreNearby: Boolean,
    val soldOutRecently: Boolean,
    val registeredRecently: Boolean,
    val photoAvailable: Boolean,
)
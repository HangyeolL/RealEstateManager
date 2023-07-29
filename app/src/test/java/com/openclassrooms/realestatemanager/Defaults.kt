package com.openclassrooms.realestatemanager

import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.data.local.model.AgentEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstatePhotoEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstateWithPhotos
import com.openclassrooms.realestatemanager.design_system.autocomplete_text_view.AutocompleteTextViewState
import com.openclassrooms.realestatemanager.design_system.real_estate_agent.RealEstateAgentSpinnerItemViewState
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoItemViewState
import com.openclassrooms.realestatemanager.design_system.real_estate_type.RealEstateTypeSpinnerItemViewState
import com.openclassrooms.realestatemanager.domain.autocomplete.model.AutocompleteEntity
import com.openclassrooms.realestatemanager.domain.search_criteria.model.SearchCriteria

fun getDefaultRealEstateEntity(realEstateId: Int) = RealEstateEntity(
    realEstateId = realEstateId,
    type = "type$realEstateId",
    descriptionBody = "description$realEstateId",
    squareMeter = realEstateId * 10,
    city = "city$realEstateId",
    price = realEstateId * 1000,
    numberOfBedrooms = realEstateId + 1,
    numberOfBathrooms = realEstateId + 1,
    numberOfRooms = realEstateId + 2,
    address = "address$realEstateId",
    garage = false,
    guard = false,
    garden = false,
    elevator = false,
    groceryStoreNearby = false,
    isSoldOut = false,
    dateOfSold = null,
    marketSince = "0$realEstateId/06/2023",
    agentIdInCharge = realEstateId,
    latLng = LatLng(realEstateId.toDouble(), realEstateId.toDouble())
)

fun getDefaultAgentEntity(agentId: Int) = AgentEntity(
    agentId = agentId,
    name = "agentName$agentId",
    email = "agentName$agentId@email.com",
    photoUrl = "agentPhoto$agentId"
)

fun getDefaultRealEstatePhoto(photoId: Int, realEstateId: Int) = RealEstatePhotoEntity(
    photoId = photoId.toLong(),
    realEstateIdOfPhoto = realEstateId.toLong(),
    url = "url$photoId",
    description = null,
)

fun getDefaultRealEstatePhotoList(realEstateId: Int, sizeCount: Int = 3) =
    List(sizeCount) { index ->
        getDefaultRealEstatePhoto(
            photoId = index,
            realEstateId = realEstateId
        )
    }

fun getDefaultRealEstateWithPhotos(realEstateId: Int) = RealEstateWithPhotos(
    getDefaultRealEstateEntity(realEstateId),
    getDefaultRealEstatePhotoList(realEstateId)
)

fun getDefaultRealEstateListWithPhotos(size: Int = 5) = List(size) { index ->
    getDefaultRealEstateWithPhotos(index)
}

fun getDefaultAgentList(sizeCount: Int = 5) =
    List(sizeCount) { index ->
        getDefaultAgentEntity(
            agentId = index,
        )
    }

fun getDefaultSearchCriteria() = SearchCriteria(
    type = null,
    city = null,
    agentIdInCharge = null,
    numberOfBathrooms = null,
    numberOfBedrooms = null,
    minSquareMeter = null,
    maxSquareMeter = null,
    minPrice = null,
    maxPrice = null,
    garage = null,
    guard = null,
    garden = null,
    elevator = null,
    groceryStoreNearby = null,
    soldOutRecently = null,
    registeredRecently = null,
    photoAvailable = null
)

fun getDefaultRealEstatePhotoItemViewState(photoId: Int) =
    RealEstatePhotoItemViewState.Content(
        photoId = photoId.toLong(),
        photoUrl = "url$photoId",
        photoDescription = null
    )

fun getDefaultRealEstatePhotoItemViewStateList(sizeCount: Int = 3) =
    List(sizeCount) { index ->
        getDefaultRealEstatePhotoItemViewState(index)
    }

fun getDefaultAutocompleteEntity(placeId: String) = AutocompleteEntity(
    placeId = placeId,
    text = "text of $placeId",
)

fun getDefaultAutocompleteEntityList(sizeCount: Int = 5) =
    List(sizeCount) { index ->
        getDefaultAutocompleteEntity("placeId$index")
    }

fun getDefaultAutocompleteTextViewState(placeId: String) = AutocompleteTextViewState(
    text = "text of $placeId"
)

fun getDefaultAutocompleteTextViewStateList(sizeCount: Int = 5) =
    List(sizeCount) { index ->
        getDefaultAutocompleteTextViewState("placeId$index")
    }

fun getDefaultRealEstateTypeSpinnerItemViewStateList() =
    listOf(
        RealEstateTypeSpinnerItemViewState(
            icon = R.drawable.ic_baseline_house_24,
            type = "House"
        ),
        RealEstateTypeSpinnerItemViewState(
            icon = R.drawable.ic_baseline_apartment_24,
            type = "Apartment"
        ),
        RealEstateTypeSpinnerItemViewState(
            icon = R.drawable.ic_baseline_bed_24,
            type = "Studio"
        )
    )

fun getDefaultAgentSpinnerItemViewState(agentId: Int) =
    RealEstateAgentSpinnerItemViewState(
        agentIdInCharge = agentId,
        agentNameInCharge = "agentName$agentId",
        agentPhoto = "agentPhoto$agentId"
    )

fun getDefaultAgentSpinnerItemViewStateList(sizeCount: Int = 5) =
    List(sizeCount) { index ->
        getDefaultAgentSpinnerItemViewState(index)
    }





package com.openclassrooms.realestatemanager

import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.data.local.model.AgentEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstateEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstatePhotoEntity
import com.openclassrooms.realestatemanager.data.local.model.RealEstateWithPhotos
import com.openclassrooms.realestatemanager.domain.search_criteria.model.SearchCriteria

fun getDefaultRealEstateEntity(realEstateId: Int) = RealEstateEntity(
    realEstateId = realEstateId,
    type = "House",
    descriptionBody = "",
    squareMeter = realEstateId * 10,
    city = "Fontainebleau",
    price = realEstateId * 1000,
    numberOfBedrooms = realEstateId + 1,
    numberOfBathrooms = realEstateId + 1,
    numberOfRooms = realEstateId + 2,
    address = "Somewhere $realEstateId",
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
    name = "AGENT:$agentId",
    email = "AGENT:$agentId@email.com",
    photoUrl = "PhotoUrl $agentId"
)

fun getDefaultRealEstatePhoto(photoId: Int, realEstateId: Int) = RealEstatePhotoEntity(
    photoId = photoId.toLong(),
    realEstateIdOfPhoto = realEstateId.toLong(),
    url = "url",
    description = null,
)

fun getDefaultRealEstatePhotoList(realEstateId: Int, realEstatePhotoCount: Int = 3) =
    List(realEstatePhotoCount) { index ->
        getDefaultRealEstatePhoto(
            photoId = index,
            realEstateId = realEstateId
        )
    }

fun getDefaultRealEstateWithPhotos(realEstateId: Int) = RealEstateWithPhotos(
    getDefaultRealEstateEntity(realEstateId),
    getDefaultRealEstatePhotoList(realEstateId)
)

fun getDefaultRealEstatesWithPhotosList(size: Int = 5) = List(size) { index ->
    getDefaultRealEstateWithPhotos(index + 1)
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


package com.openclassrooms.realestatemanager.ui.add_photo

//@HiltViewModel
//class AddPhotoDialogViewModel @Inject constructor(
//    private val realEstateRepository: RealEstateRepository
//) : ViewModel() {
//
//    fun onButtonOkClicked(realEstateId: Int, picUriToString: String, description: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            realEstateRepository.insertRealEstatePhoto(
//                RealEstatePhotoEntity(
//                    realEstateIdOfPhoto = realEstateId,
//                    url = picUriToString,
//                    description = description,
//                )
//            ) {
//
//            }
//        }
//    }
//}
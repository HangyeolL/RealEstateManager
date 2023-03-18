package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.net.Uri

sealed class AddOrModifyRealEstateViewAction {

    object NavigateToMainActivity : AddOrModifyRealEstateViewAction()

    object OpenCamera: AddOrModifyRealEstateViewAction()

}
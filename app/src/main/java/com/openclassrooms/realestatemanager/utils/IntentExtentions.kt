package com.openclassrooms.realestatemanager.utils

import android.content.Intent

fun Intent.getIntExtraSafe(key: String) : Int? = getIntExtra(key, -1).takeIf { it != -1 }
package com.example.virtual_assigment.util

import android.app.Activity
import android.content.Intent
import com.example.virtual_assigment.ui.entities.EntitiesActivity


object ScreenNavigator {
    fun navigateToInformationCollectActivity(activity: Activity?){
        activity?.startActivity(Intent(activity, EntitiesActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        activity?.finish()
    }
}
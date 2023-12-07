package com.teamx.hatlyDriver

import androidx.lifecycle.MutableLiveData
import com.teamx.hatlyDriver.baseclasses.BaseViewModel


/**
 * Shared View Model class for sharing data between fragments
 */
class SharedViewModel : BaseViewModel() {

    val clickOnContinueBtn: MutableLiveData<Boolean>? = null


}
package com.teamx.hatlyDriver.ui.fragments.orders

import com.teamx.hatlyDriver.baseclasses.BaseViewModel
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.teamx.hatlyDriver.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {



}
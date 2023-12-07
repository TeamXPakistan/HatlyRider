package com.teamx.hatlyDriver.ui.fragments.orders.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.baseclasses.BaseViewModel
import com.teamx.hatlyDriver.data.dataclasses.pastParcels.GetPastParcelsData
import com.teamx.hatlyDriver.data.dataclasses.pastorder.PastOrdersData
import com.teamx.hatlyDriver.data.dataclasses.sucess.SuccessData
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.teamx.hatlyDriver.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ActiveViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _getPastOrdersResponse = MutableLiveData<Resource<PastOrdersData>>()
    val getPastOrdersResponse: LiveData<Resource<PastOrdersData>>
        get() = _getPastOrdersResponse

    fun getPastOrders(page: Int, limit: Int, status: String) {
        viewModelScope.launch {
            _getPastOrdersResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getPastOrders(page, limit, status).let {
                        if (it.isSuccessful) {
                            _getPastOrdersResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _getPastOrdersResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getPastOrdersResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getPastOrdersResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getPastOrdersResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getPastOrdersResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getPastOrdersResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _getPastParcelsResponse = MutableLiveData<Resource<GetPastParcelsData>>()
    val getPastParcelsResponse: LiveData<Resource<GetPastParcelsData>>
        get() = _getPastParcelsResponse

    fun getPastParcels(page: Int, limit: Int, status: String) {
        viewModelScope.launch {
            _getPastParcelsResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getPastParcels(page, limit, status).let {
                        if (it.isSuccessful) {
                            _getPastParcelsResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _getPastParcelsResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getPastParcelsResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getPastParcelsResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getPastParcelsResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getPastParcelsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getPastParcelsResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _pickedDispatchOrderResponse = MutableLiveData<Resource<SuccessData>>()
    val pickedDispatchOrderResponse: LiveData<Resource<SuccessData>>
        get() = _pickedDispatchOrderResponse

    fun pickedDispatchOrder(id: String, param: JsonObject) {
        viewModelScope.launch {
            _pickedDispatchOrderResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.pickedDispatchOrder(id, param).let {
                        if (it.isSuccessful) {
                            _pickedDispatchOrderResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
                            _pickedDispatchOrderResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _pickedDispatchOrderResponse.postValue(
                                Resource.error(
                                    jsonObj.getString(
                                        "message"
                                    )
                                )
                            )
                        } else {
                            _pickedDispatchOrderResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _pickedDispatchOrderResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _pickedDispatchOrderResponse.postValue(
                Resource.error(
                    "No internet connection",
                    null
                )
            )
        }
    }


}
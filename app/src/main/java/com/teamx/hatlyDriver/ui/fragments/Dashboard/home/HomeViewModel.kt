package com.teamx.hatlyDriver.ui.fragments.Dashboard.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.baseclasses.BaseViewModel
import com.teamx.hatlyDriver.data.dataclasses.fcmmodel.FcmModel
import com.teamx.hatlyDriver.data.dataclasses.pastParcels.GetPastParcelsData
import com.teamx.hatlyDriver.data.dataclasses.pastorder.PastOrdersData
import com.teamx.hatlyDriver.data.dataclasses.sucess.SuccessData
import com.teamx.hatlyDriver.data.dataclasses.totalEarning.TotalEarningData
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.teamx.hatlyDriver.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _totalEarningsResponse = MutableLiveData<Resource<TotalEarningData>>()
    val totalEarningsResponse: LiveData<Resource<TotalEarningData>>
        get() = _totalEarningsResponse

    fun getTotalEarnings(filterBy: String,filterFor: String) {
        viewModelScope.launch {
            _totalEarningsResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.getTotalEarning(filterBy,filterFor).let {
                        if (it.isSuccessful) {
                            _totalEarningsResponse.postValue(Resource.success(it.body()!!))
                        }/*else if (it.code() == 401) {
                            _totalEarningsResponse.postValue(Resource.unAuth("", null))
                        }*/
                        else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _totalEarningsResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _totalEarningsResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _totalEarningsResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _totalEarningsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _totalEarningsResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _acceptRejectResponse = MutableLiveData<Resource<SuccessData>>()
    val acceptRejectResponse: LiveData<Resource<SuccessData>>
        get() = _acceptRejectResponse

    fun acceptReject(id: String, param: JsonObject) {
        viewModelScope.launch {
            _acceptRejectResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.acceptRejectOrder(id, param).let {
                        if (it.isSuccessful) {
                            _acceptRejectResponse.postValue(Resource.success(it.body()!!))
                        }
                        else if (it.code() == 401) {
                            _acceptRejectResponse.postValue(Resource.unAuth("", null))
                        }
                        else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _acceptRejectResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _acceptRejectResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _acceptRejectResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _acceptRejectResponse.postValue(Resource.error("No internet connection", null))
        }
    }



    private val _offlineReasonResponse = MutableLiveData<Resource<SuccessData>>()
    val offlineReasonResponse: LiveData<Resource<SuccessData>>
        get() = _offlineReasonResponse

    fun offlineReason(id: String, param: JsonObject) {
        viewModelScope.launch {
            _offlineReasonResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.offlineReason(id, param).let {
                        if (it.isSuccessful) {
                            _offlineReasonResponse.postValue(Resource.success(it.body()!!))
                        }
                        else if (it.code() == 401) {
                            _offlineReasonResponse.postValue(Resource.unAuth("", null))
                        }
                        else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _offlineReasonResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _offlineReasonResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _offlineReasonResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _offlineReasonResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _fcmResponse = MutableLiveData<Resource<FcmModel>>()
    val fcmResponse: LiveData<Resource<FcmModel>>
        get() = _fcmResponse

    fun fcm(param: JsonObject) {
        viewModelScope.launch {
            _fcmResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.fcm(param).let {
                        if (it.isSuccessful) {
                            _fcmResponse.postValue(Resource.success(it.body()!!))
                        }
                       /* else if (it.code() == 401) {
                            _getPastOrdersResponse.postValue(Resource.unAuth("", null))
                        }*/
                        else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _fcmResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                           /* val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _fcmResponse.postValue(Resource.error(jsonObj.getString("message")))*/
//                            _fcmResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _fcmResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _fcmResponse.postValue(Resource.error("No internet connection", null))
        }
    }


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
                        }
                        else if (it.code() == 401) {
                            _getPastOrdersResponse.postValue(Resource.unAuth("", null))
                        }
                        else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getPastOrdersResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
//                            _getPastOrdersResponse.postValue(
                               /* Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )*/
//                            Timber.tag("87878787887").d("third")

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
                        } /*else if (it.code() == 401) {
                            _getPastParcelsResponse.postValue(Resource.unAuth("", null))
                        }*/
                        else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getPastParcelsResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
//                            _getPastParcelsResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            /*_getPastParcelsResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )*/
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getPastParcelsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getPastParcelsResponse.postValue(Resource.error("No internet connection", null))
        }
    }
}
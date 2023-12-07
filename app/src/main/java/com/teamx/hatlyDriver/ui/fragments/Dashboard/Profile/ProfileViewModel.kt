package com.teamx.hatlyDriver.ui.fragments.Dashboard.Profile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.hatlyDriver.baseclasses.BaseViewModel
import com.teamx.hatlyDriver.data.dataclasses.meModel.me.MeModel
import com.teamx.hatlyDriver.data.dataclasses.sucess.SuccessData
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.teamx.hatlyDriver.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _meResponse = MutableLiveData<Resource<MeModel>>()
    val meResponse: LiveData<Resource<MeModel>>
        get() = _meResponse
    fun me() {
        viewModelScope.launch {
            _meResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.me().let {
                        if (it.isSuccessful) {
                            _meResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _meResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _meResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _meResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _meResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _meResponse.postValue(Resource.error("No internet connection", null))
        }
    }

 private val _logoutResponse = MutableLiveData<Resource<SuccessData>>()
    val logoutResponse: LiveData<Resource<SuccessData>>
        get() = _logoutResponse
    fun logout() {
        viewModelScope.launch {
            _logoutResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.logout().let {
                        if (it.isSuccessful) {
                            _logoutResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _logoutResponse.postValue(Resource.error(jsonObj.getString("logoutssage")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _logoutResponse.postValue(Resource.error(jsonObj.getString("logoutssage")))
//                            _logoutResponse.postValue(Resource.error(it.logoutssage(), null))
                        }
                    }
                } catch (e: Exception) {
                    _logoutResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _logoutResponse.postValue(Resource.error("No internet connection", null))
        }
    }


}
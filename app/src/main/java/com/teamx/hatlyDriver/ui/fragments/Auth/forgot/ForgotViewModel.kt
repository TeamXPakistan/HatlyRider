package com.teamx.hatlyDriver.ui.fragments.Auth.forgot


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.baseclasses.BaseViewModel
import com.teamx.hatlyDriver.data.dataclasses.forgotPass.ForgotData
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.teamx.hatlyDriver.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _forgotPassPhoneResponse = MutableLiveData<Resource<ForgotData>>()
    val forgotPassPhoneResponse: LiveData<Resource<ForgotData>>
        get() = _forgotPassPhoneResponse
    fun forgotPassPhone(param : JsonObject) {
        viewModelScope.launch {
            _forgotPassPhoneResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.forogtPassPhone(param) .let {
                        if (it.isSuccessful) {
                            _forgotPassPhoneResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _forgotPassPhoneResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _forgotPassPhoneResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _forgotPassPhoneResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _forgotPassPhoneResponse.postValue(Resource.error("No internet connection", null))
        }
    }


}
package com.teamx.hatlyDriver.ui.fragments.Auth.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.baseclasses.BaseViewModel
import com.teamx.hatlyDriver.data.dataclasses.login.LoginData
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.teamx.hatlyDriver.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _loginResponse = MutableLiveData<Resource<LoginData>>()
    val loginResponse: LiveData<Resource<LoginData>>
        get() = _loginResponse

    fun loginPhone(param: JsonObject/*, unAuthorizedCallback: UnAuthorizedCallback*/) {
        viewModelScope.launch {
            _loginResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.loginPhone(param).let {
                        if (it.isSuccessful) {
                            _loginResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
                            _loginResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _loginResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _loginResponse.postValue(Resource.error(it.message(), null))
                        } else {
                            _loginResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _loginResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _loginResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}
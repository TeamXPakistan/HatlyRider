package com.teamx.hatlyDriver.ui.fragments.Auth.singup


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.baseclasses.BaseViewModel
import com.teamx.hatlyDriver.data.dataclasses.SignUp.RegisterData
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.teamx.hatlyDriver.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _signupResponse = MutableLiveData<Resource<RegisterData>>()

    val signupResponse: LiveData<Resource<RegisterData>> get() = _signupResponse

    fun signup(param: JsonObject) {
        viewModelScope.launch {

            _signupResponse.postValue(Resource.loading(null))

            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.signup(param).let {
                        if (it.isSuccessful) {
                            _signupResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _signupResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _signupResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _signupResponse.postValue(Resource.error("${e.message}", null))
                }
            } else {
                _signupResponse.postValue(Resource.error("No internet connection", null))
            }

        }
    }

}
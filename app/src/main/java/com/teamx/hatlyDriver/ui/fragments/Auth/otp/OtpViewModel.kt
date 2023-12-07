package com.teamx.hatlyDriver.ui.fragments.Auth.otp


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.baseclasses.BaseViewModel
import com.teamx.hatlyDriver.data.dataclasses.login.LoginData
import com.teamx.hatlyDriver.data.dataclasses.model.ModelVerifyPassOtp
import com.teamx.hatlyDriver.data.dataclasses.sucess.SuccessData
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.teamx.hatlyDriver.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _verifySignupOtp = MutableLiveData<Resource<LoginData>>()
    val verifySignupOtpResponse: LiveData<Resource<LoginData>>
        get() = _verifySignupOtp

    fun verifySignupOtp(param: JsonObject) {
        viewModelScope.launch {
            _verifySignupOtp.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.otpVerify(param).let {
                        if (it.isSuccessful) {
                            _verifySignupOtp.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _verifySignupOtp.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _verifySignupOtp.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _verifySignupOtp.postValue(Resource.error("${e.message}", null))
                }
            } else _verifySignupOtp.postValue(Resource.error("No internet connection", null))
        }
    }





    private val _forgotPassVerifyOtp = MutableLiveData<Resource<ModelVerifyPassOtp>>()
    val forgotPassVerifyOtpResponse: LiveData<Resource<ModelVerifyPassOtp>>
        get() = _forgotPassVerifyOtp

    fun forgotPassVerifyOtp(param: JsonObject) {
        viewModelScope.launch {
            _forgotPassVerifyOtp.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.otpVerifyForgot(param).let {
                        if (it.isSuccessful) {
                            _forgotPassVerifyOtp.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _forgotPassVerifyOtp.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _forgotPassVerifyOtp.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _forgotPassVerifyOtp.postValue(Resource.error("${e.message}", null))
                }
            } else _forgotPassVerifyOtp.postValue(Resource.error("No internet connection", null))
        }
    }






    private val _resend_otp = MutableLiveData<Resource<SuccessData>>()
    val resendOtpResponse: LiveData<Resource<SuccessData>>
        get() = _resend_otp

    fun resendOtp(param: JsonObject) {
        viewModelScope.launch {
            _resend_otp.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.resendOtp(param).let {
                        if (it.isSuccessful) {
                            _resend_otp.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _resend_otp.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _resend_otp.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _resend_otp.postValue(Resource.error("${e.message}", null))
                }
            } else _resend_otp.postValue(Resource.error("No internet connection", null))
        }
    }

}
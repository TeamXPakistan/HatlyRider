package com.teamx.hatlyDriver.ui.fragments.accountSetting


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.baseclasses.BaseViewModel
import com.teamx.hatlyDriver.data.dataclasses.login.LoginData
import com.teamx.hatlyDriver.data.dataclasses.meModel.me.MeModel
import com.teamx.hatlyDriver.data.dataclasses.modelUploadImages.ModelUploadImages
import com.teamx.hatlyDriver.data.dataclasses.sucess.SuccessData
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.teamx.hatlyDriver.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
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
                        }
                        else if (it.code() == 401) {
                            _meResponse.postValue(Resource.unAuth("", null))
                        }else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
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


    private val _changePasswordResponse = MutableLiveData<Resource<SuccessData>>()
    val changePasswordResponse: LiveData<Resource<SuccessData>>
        get() = _changePasswordResponse

    fun changePassword(params: JsonObject) {
        viewModelScope.launch {
            _changePasswordResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.changePassword(params).let {
                        if (it.isSuccessful) {
                            _changePasswordResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _changePasswordResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _changePasswordResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _changePasswordResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _changePasswordResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _changePasswordResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _uploadReviewImgResponse = MutableLiveData<Resource<ModelUploadImages>>()
    val uploadReviewImgResponse: LiveData<Resource<ModelUploadImages>>
        get() = _uploadReviewImgResponse

    fun uploadReviewImg(imageParts: List<MultipartBody.Part>) {
        viewModelScope.launch {
            _uploadReviewImgResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.uploadReviewImg(imageParts).let {
                        if (it.isSuccessful) {
                            _uploadReviewImgResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _uploadReviewImgResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj ${it.code()}: ${jsonObj.getString("message")}")
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _uploadReviewImgResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _uploadReviewImgResponse.postValue(Resource.error("${e.message}", null))
                }
            } else{
                _uploadReviewImgResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }


    private val _updateProfileResponse = MutableLiveData<Resource<LoginData>>()
    val updateProfileResponse: LiveData<Resource<LoginData>>
        get() = _updateProfileResponse

    fun updateProfile(params: JsonObject,) {
        viewModelScope.launch {
            _updateProfileResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.updateProfile(params).let {
                        if (it.isSuccessful) {
                            _updateProfileResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateProfileResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj ${it.code()}: ${jsonObj.getString("message")}")
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateProfileResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _updateProfileResponse.postValue(Resource.error("${e.message}", null))
                }
            } else{
                _updateProfileResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }

}
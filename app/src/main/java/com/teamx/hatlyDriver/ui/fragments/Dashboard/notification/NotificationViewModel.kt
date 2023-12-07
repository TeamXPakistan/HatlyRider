package com.teamx.hatlyDriver.ui.fragments.Dashboard.notification



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.hatlyDriver.baseclasses.BaseViewModel
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.teamx.hatlyDriver.ui.fragments.Dashboard.notification.modelNotification.ModelNotification
import com.teamx.hatlyDriver.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _notificationResponse = MutableLiveData<Resource<ModelNotification>>()
    val notificationResponse: LiveData<Resource<ModelNotification>>
        get() = _notificationResponse

    fun notification() {
        viewModelScope.launch {
            _notificationResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.notification().let {
                        if (it.isSuccessful) {
                            _notificationResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _notificationResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _notificationResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _notificationResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _notificationResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}
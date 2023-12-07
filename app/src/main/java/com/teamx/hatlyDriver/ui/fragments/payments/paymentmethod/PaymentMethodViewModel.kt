package com.teamx.hatlyDriver.ui.fragments.payments.paymentmethod


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.baseclasses.BaseViewModel
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.teamx.hatlyDriver.ui.fragments.payments.paymentmethod.defaultmodel.ModelDefaultCredCards
import com.teamx.hatlyDriver.ui.fragments.payments.paymentmethod.modelDetach.ModelDetachCredCards
import com.teamx.hatlyDriver.ui.fragments.payments.paymentmethod.modelGetCards.ModelCredCards
import com.teamx.hatlyDriver.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class PaymentMethodViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _credCardsGoogle = MutableLiveData<Resource<ModelCredCards>>()
    val credCardsResponse: LiveData<Resource<ModelCredCards>>
        get() = _credCardsGoogle
    fun credCards() {
        viewModelScope.launch {
            _credCardsGoogle.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.getCredCards().let {
                        if (it.isSuccessful) {
                            _credCardsGoogle.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _credCardsGoogle.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _credCardsGoogle.postValue(Resource.error(jsonObj.getString("message")))
//                            _credCardsGoogle.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _credCardsGoogle.postValue(Resource.error("${e.message}", null))
                }
            } else _credCardsGoogle.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _defaultCredCardsGoogle = MutableLiveData<Resource<ModelDefaultCredCards>>()
    val defaultCredCardsResponse: LiveData<Resource<ModelDefaultCredCards>>
        get() = _defaultCredCardsGoogle
    fun defaultCredCards(params: JsonObject) {
        viewModelScope.launch {
            _defaultCredCardsGoogle.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.setDefaultCredCards(params).let {
                        if (it.isSuccessful) {
                            _defaultCredCardsGoogle.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _defaultCredCardsGoogle.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _defaultCredCardsGoogle.postValue(Resource.error(jsonObj.getString("message")))
//                            _defaultCredCardsGoogle.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _defaultCredCardsGoogle.postValue(Resource.error("${e.message}", null))
                }
            } else _defaultCredCardsGoogle.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _detachCredCardsGoogle = MutableLiveData<Resource<ModelDetachCredCards>>()
    val detachCredCardsResponse: LiveData<Resource<ModelDetachCredCards>>
        get() = _detachCredCardsGoogle
    fun detachCredCards(params: JsonObject) {
        viewModelScope.launch {
            _detachCredCardsGoogle.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.setDetachCredCards(params).let {
                        if (it.isSuccessful) {
                            _detachCredCardsGoogle.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _detachCredCardsGoogle.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _detachCredCardsGoogle.postValue(Resource.error(jsonObj.getString("message")))
//                            _detachCredCardsGoogle.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _detachCredCardsGoogle.postValue(Resource.error("${e.message}", null))
                }
            } else _detachCredCardsGoogle.postValue(Resource.error("No internet connection", null))
        }
    }

}
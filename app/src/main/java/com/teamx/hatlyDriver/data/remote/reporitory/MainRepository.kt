package com.teamx.hatlyDriver.data.remote.reporitory

import com.google.gson.JsonObject
import com.teamx.hatlyDriver.data.local.db.AppDao
import com.teamx.hatlyDriver.data.local.dbModel.CartDao
import com.teamx.hatlyDriver.data.remote.ApiService
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService, var localDataSource: AppDao, var localDataSource2: CartDao
) {
    suspend fun loginPhone(@Body param: JsonObject) = apiService.loginPhone(param)

    suspend fun signup(@Body param: JsonObject) = apiService.signup(param)

    suspend fun otpVerify(@Body param: JsonObject) = apiService.otpVerify(param)

    suspend fun updatePass(@Body param: JsonObject) = apiService.updatePass(param)


    suspend fun changePassword(@Body params: JsonObject) = apiService.changePassword(params)
    suspend fun otpVerifyForgot(@Body param: JsonObject) = apiService.otpVerifyForgot(param)

    suspend fun resendOtp(@Body param: JsonObject) = apiService.resendOtp(param)

    suspend fun forogtPassPhone(@Body param: JsonObject) = apiService.forgotPassPhone(param)
    suspend fun resetPassPhone(@Body param: JsonObject) = apiService.resetPassPhone(param)

    suspend fun notification() = apiService.notification()

    suspend fun setDefaultCredCards(
        @Body params: JsonObject
    ) = apiService.setDefaultCredCards(params)

    suspend fun offlineReason(
        @Path("id") id: String,
        @Body param: JsonObject,
    ) = apiService.offlineReason(id,param)

    suspend fun me() = apiService.me()
    suspend fun getTransactionHistory(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ) = apiService.getTransactionHistory(limit, page)

    suspend fun getTotalEarning(
        @Query("filterBy") filterBy: String,
        @Query("filterFor") filterFor: String,
    ) = apiService.getTotalEarning(filterBy, filterFor)

    suspend fun setDetachCredCards(
        @Body params: JsonObject
    ) = apiService.setDetachCredCards(params)

    suspend fun topUpSaved(
        @Body params: JsonObject
    ) = apiService.topUpSaved(params)

    suspend fun getCredCards(
    ) = apiService.getCredCards()

    suspend fun uploadReviewImg(
        @Part images: List<MultipartBody.Part>
    ) = apiService.uploadReviewImg(images)

    suspend fun updateProfile(
        @Body params: JsonObject,
    ) = apiService.updateProfile(params)

    suspend fun logout(
    ) = apiService.logout()

    suspend fun getPastOrders(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("status") status: String
    ) = apiService.getPastOrders(page, limit, status)

    suspend fun getPastParcels(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("status") status: String
    ) = apiService.getPastParcels(page, limit, status)

    suspend fun fcm(@Body param: JsonObject) = apiService.fcm(param)


    suspend fun getOrdersByStatus(
        @Query("status") status: String
    ) = apiService.getOrdersByStatus(status)

    suspend fun getActiveOrders(
        @Query("status") status: String,
        @Query("requestFor") requestFor: String
    ) = apiService.getActiveOrders(status, requestFor)


    suspend fun acceptRejectOrder(
        @Path("id") id: String, @Body param: JsonObject
    ) = apiService.acceptRejectOrder(id, param)

    suspend fun pickedDispatchOrder(
        @Path("id") id: String, @Body param: JsonObject
    ) = apiService.pickedDispatchOrder(id, param)


}
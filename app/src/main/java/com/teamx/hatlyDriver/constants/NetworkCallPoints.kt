package com.teamx.hatlyDriver.constants

class NetworkCallPoints {
    companion object {
        const val LOGIN_PHONE = "auth/login/app";
        const val SIGN_UP = "driver/auth/signup";
        const val UPDATE_PROFILE = "user/update"
        const val ME = "auth/me"
        const val LOGOUT = "auth/logout"



        // Register Otp Verify

        const val OTP_VERIFY = "auth/verify/signup/otp";
        const val UPDATE_PASS = "auth/password/update/app"
        const val VERIFY_FORGOT_PASS = "auth/verify/forgot/otp";
        const val RESEND_OTP_VERIFY = "auth/resend/otp";


        // Create new password
        const val RESET_PASS = "reset-password";
        const val RESET_PASS_PHONE = "Reset-Password-Phone";
        const val CHANGE_PASSWORD = "auth/update-profile"



        const val UPLOAD_REVIEW_IMGS = "upload/uploadMultiple"

        const val FORGOT_PASS_PHONE = "auth/forgot/password/app";
        const val FCM_TOKEN = "auth/addToken"


        const val NOTIFICATION_LIST = "notification/all"

        const val GET_PAST_ORDERS = "incoming-requests/pastOrders";
        const val GET_PAST_PARCELS = "incoming-requests/pastParcels";
        const val GET_ORDERS_BYSTATUS = "incoming-requests/all";
        const val GET_ACTIVE_ORDER = "incoming-requests/all";

        const val ACCEPT_REJECT_ORDER = "incoming-requests/response/{id}";
        const val PICKED_DISPATCH_ORDER = "incoming-requests/{id}";


        const val CREDS_CARDS = "stripe/list/paymentMethods"
        const val WALLET_TOPUP = "wallet/topup"
        const val DEFAULT_CREDS_CARDS = "stripe/set/paymentMethod"
        const val DETACH_CREDS_CARDS = "stripe/detach/paymentMethod"
        const val TRANSACTION_HISTORY = "incoming-requests/transaction-history"




        const val TOTAL_EARNING = "incoming-requests/stat"
        const val OFFLINE_REASON = "offlineReason/update"





        var TOKENER = ""
    }
}
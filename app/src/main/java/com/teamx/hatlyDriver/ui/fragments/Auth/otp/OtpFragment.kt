package com.teamx.hatlyDriver.ui.fragments.Auth.otp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentOtpBinding
import com.teamx.hatlyDriver.utils.PrefHelper
import com.teamx.hatlyDriver.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONException

@AndroidEntryPoint
class OtpFragment : BaseFragment<FragmentOtpBinding, OtpViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_otp
    override val viewModel: Class<OtpViewModel>
        get() = OtpViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var pinView: String? = null
    private var phone: String? = ""
    private var fromSignup = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


        val bundle = arguments
        phone = bundle?.getString("phone")
        fromSignup = bundle?.getBoolean("fromSignup", false)!!

        mViewDataBinding.btnSend.setOnClickListener {
            if (isValidate()) {
                initialization()
                if (fromSignup) {
                    Log.d("verifySignupO", "fromSignup: true")
                    mViewModel.verifySignupOtp(createSignUpVerifyParams())
                } else {
                    Log.d("verifySignupO", "fromSignup: false")
                    mViewModel.forgotPassVerifyOtp(createSignUpVerifyParams())
                }
            }
        }

        if (!mViewModel.verifySignupOtpResponse.hasActiveObservers()) {
            mViewModel.verifySignupOtpResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }  Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        onToSignUpPage()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            Log.d("verifySignupO", "onViewCreated: $data")
//                            CoroutineScope(Dispatchers.Main).launch {
//                                data.token.let { it1 ->
//                                    dataStoreProvider.saveUserToken(it1)
//                                }
//                                navController =
//                                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
//                                navController.navigate(R.id.homeFragment, bundle, options)
//                            }


                            lifecycleScope.launch(Dispatchers.Main) {
                                dataStoreProvider.saveUserToken(data.token)

                                navController =
                                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                                navController.navigate(R.id.homeFragment, arguments, options)
                            }


                            PrefHelper.getInstance(requireActivity()).setUserData(data)

                         /*   navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                            navController.navigate(R.id.homeFragment, bundle, options)*/

                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                        Log.d("verifySignupO", "onViewCreated: ${it.message!!}")
                    }
                }
            }
        }

        if (!mViewModel.forgotPassVerifyOtpResponse.hasActiveObservers()) {
            mViewModel.forgotPassVerifyOtpResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }  Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        onToSignUpPage()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            val bundle = Bundle()
                            bundle.putString("uniqueId", data.uniqueId)
                            bundle.putString("phone", phone)
//                            if (data.verified) {
                            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                            navController.navigate(R.id.createNewPassFragment, bundle, options)

//
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            })
        }

        mViewDataBinding.btnResend.isEnabled = false
        timerStart()
        mViewDataBinding.btnResend.setOnClickListener {
            mViewModel.resendOtp(createVerifyForgotPassParams())
        }

        if (!mViewModel.resendOtpResponse.hasActiveObservers()) {
            mViewModel.resendOtpResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }  Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        onToSignUpPage()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.success) {
                                mViewDataBinding.root.snackbar("Otp resend")
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            })
        }

    }


    private fun initialization() {
        pinView = mViewDataBinding.pinView.text.toString()
    }

    private fun createSignUpVerifyParams(): JsonObject {
        val params = JsonObject()
        try {
            params.addProperty("contact", phone)
            params.addProperty("verificationCode", pinView)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    private fun createVerifyForgotPassParams(): JsonObject {
        val params = JsonObject()
        try {
            params.addProperty("contact", phone)
            params.addProperty("verificationCode", pinView)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    private fun isValidate(): Boolean {
        if (mViewDataBinding.pinView.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter Otp")
            return false
        }
        return true
    }

    private fun timerStart() {
        val durationSeconds = 30
        var remainingSeconds = durationSeconds

        lifecycleScope.launch {
            while (remainingSeconds > 0) {
                println("Remaining time: $remainingSeconds seconds")
                Log.d("timerStart", "working $remainingSeconds")
                mViewDataBinding.timer.text = "Resend OTP in $remainingSeconds sec"
                delay(1000) // Delay for 1 second
                remainingSeconds--
            }

            println("Time's up!")
            Log.d("timerStart", "Time's up!")
            mViewDataBinding.btnResend.isEnabled = true
        }
    }

}
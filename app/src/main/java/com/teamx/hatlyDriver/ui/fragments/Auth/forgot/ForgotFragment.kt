package com.teamx.hatlyDriver.ui.fragments.Auth.forgot

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentForgotBinding
import com.teamx.hatlyDriver.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import timber.log.Timber

@AndroidEntryPoint
class ForgotFragment : BaseFragment<FragmentForgotBinding, ForgotViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_forgot
    override val viewModel: Class<ForgotViewModel>
        get() = ForgotViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var userEmail: String? = null

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

        mViewDataBinding.btnSend.setOnClickListener {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.otpFragment, null, options)
        }

        mViewDataBinding.btnBack.setOnClickListener {
            popUpStack()
        }

        mViewDataBinding.btnLogin.setOnClickListener {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.logInFragment, null, options)
        }

        mViewDataBinding.btnSend.setOnClickListener {
            isValidate()
        }

    }

    private fun initialization() {
        userEmail = mViewDataBinding.etPhone.text.toString().trim()
    }

    override fun subscribeToNetworkLiveData() {
        super.subscribeToNetworkLiveData()

        initialization()

        if (!userEmail!!.isEmpty()) {

            val params = JsonObject()
            try {
                params.addProperty("contact", userEmail)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

          Timber.tag("UserData").d( params.toString())

            mViewModel.forgotPassPhone(params)

            mViewModel.forgotPassPhoneResponse.observe(requireActivity(), Observer {
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
                                val bundle = Bundle()
                                bundle.putString("phone",userEmail)
                                bundle.putBoolean("fromSignup",false)
                                navController = Navigation.findNavController(
                                    requireActivity(), R.id.nav_host_fragment
                                )
                                navController.navigate(R.id.otpFragment, bundle, options)
                            }

                            val bundle = Bundle()
                            bundle.putString("phone", userEmail)


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


    fun isValidate(): Boolean {
        if (mViewDataBinding.etPhone.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(R.string.enter_phone))
            return false
        }

        subscribeToNetworkLiveData()
        return true
    }
}
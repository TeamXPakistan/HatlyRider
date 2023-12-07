package com.teamx.hatlyDriver.ui.fragments.Auth.login

import android.os.Bundle
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
import com.teamx.hatlyDriver.utils.PrefHelper
import com.teamx.hatlyDriver.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

@AndroidEntryPoint
class LogInFragment :
    BaseFragment<com.teamx.hatlyDriver.databinding.FragmentLoginBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_login
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var userEmail: String? = null
    private var password: String? = null

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


        mViewDataBinding.btnSingup.setOnClickListener {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.action_logInFragment_to_signupFragment, null, options)
        }

        mViewDataBinding.forgetPass.setOnClickListener {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.forgotFragment, null, options)
        }

        mViewDataBinding.btnBack.setOnClickListener {
            popUpStack()
        }

        mViewDataBinding.btnLogin.setOnClickListener {
            isValidate()
        }


    }

    private fun initialization() {
        userEmail = mViewDataBinding.etPhone.text.toString().trim()
        password = mViewDataBinding.etPass.text.toString().trim()
    }


     fun apiforlogin() {

        initialization()

        if (!userEmail!!.isEmpty() || !password!!.isEmpty()) {

            val params = JsonObject()
            try {
                params.addProperty("password", password)
                params.addProperty("contact", userEmail)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            mViewModel.loginPhone(params)

            if (!mViewModel.loginResponse.hasActiveObservers()) {
                mViewModel.loginResponse.observe(requireActivity(), Observer {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Resource.Status.AUTH -> {
                            loadingDialog.dismiss()
                            onToSignUpPage()
                        }

                        Resource.Status.SUCCESS -> {
                            loadingDialog.dismiss()

                            it.data?.let { data ->

                                if(data.role == "driver"){
                                    lifecycleScope.launch(Dispatchers.Main) {
                                        dataStoreProvider.saveUserToken(data.token)

                                        val bundle = Bundle()
                                        bundle.putString("userimg",data.profileImage)
                                        bundle.putString("username",data.name)

                                        navController =
                                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                                        navController.navigate(R.id.homeFragment, bundle, options)
                                    }

                                    PrefHelper.getInstance(requireActivity()).setUserData(data)
                                }
                                else{
                                    showToast("You're not authentic")
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
    }

    fun isValidate(): Boolean {
        if (mViewDataBinding.etPhone.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter Phone Number")
            return false
        }

        if (mViewDataBinding.etPass.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter You Password")
            return false
        }
        if (mViewDataBinding.etPass.text.toString().trim().length < 8) {
            mViewDataBinding.root.snackbar("Password must have atleast 8 charachters")
            return false
        }
        apiforlogin()
        return true
    }
}
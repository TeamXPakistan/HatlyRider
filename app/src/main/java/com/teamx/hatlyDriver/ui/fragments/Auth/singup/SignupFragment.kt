package com.teamx.hatlyDriver.ui.fragments.Auth.singup

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentSignupBinding
import com.teamx.hatlyDriver.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class   SignupFragment : BaseFragment<FragmentSignupBinding, SignupViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_signup
    override val viewModel: Class<SignupViewModel>
        get() = SignupViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var password: String? = null
    private var name: String? = null
    private var userNumber: String? = null


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

        mViewDataBinding.btnBack.setOnClickListener {
            popUpStack()
        }

        mViewDataBinding.btnSignup.setOnClickListener {
            isValidate()
        }

        mViewDataBinding.btnLogin.setOnClickListener {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.logInFragment, null, options)
        }




    }

    private fun initialization() {
        name = mViewDataBinding.etName.text.toString().trim()
        password = mViewDataBinding.etPass.text.toString().trim()
        userNumber = mViewDataBinding.etPhone.text.toString().trim()

    }

    fun signup() {

        initialization()

        if (userNumber!!.isNotEmpty() || password!!.isNotEmpty() || name!!.isNotEmpty()) {

            val params = JsonObject()
            try {
                params.addProperty("name", name.toString())
                params.addProperty("contact", userNumber.toString())
                params.addProperty("password", password.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            mViewModel.signup(params)

            mViewModel.signupResponse.observe(requireActivity()) {
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
                            bundle.putString("phone", data.contact)
                            bundle.putString("username",data.name)
                            bundle.putBoolean("fromSignup",true)

                            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                            navController.navigate(R.id.otpFragment, bundle, options)


                        }
                    }
                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.signupResponse.removeObservers(viewLifecycleOwner)
                }
            }
        }
    }

    private fun isValidate(): Boolean {

        if (mViewDataBinding.etName.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(com.teamx.hatlyDriver.R.string.enter_name))
            return false
        }
        if (mViewDataBinding.etName.text.toString().trim().length < 3) {
            mViewDataBinding.root.snackbar(getString(com.teamx.hatlyDriver.R.string.name_must_have_atleast_3_character_long))
            return false
        }

        if (mViewDataBinding.etName.text.toString().trim().length > 15) {
            mViewDataBinding.root.snackbar(getString(com.teamx.hatlyDriver.R.string.name_maximum))
            return false
        }

        if (mViewDataBinding.etPhone.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(com.teamx.hatlyDriver.R.string.enter_your_password))
            return false
        }
        if (mViewDataBinding.etPhone.text.toString().trim().startsWith("0")) {
            mViewDataBinding.root.snackbar(getString(com.teamx.hatlyDriver.R.string.enter_Number_with_Country_Code))
            return false
        }
        if (mViewDataBinding.etPass.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(com.teamx.hatlyDriver.R.string.enter_your_password))
            return false
        }
        if (mViewDataBinding.etPass.text.toString().trim().length < 8) {
            mViewDataBinding.root.snackbar(getString(com.teamx.hatlyDriver.R.string.password_8_character))
            return false
        }

        signup()
        return true
    }
}
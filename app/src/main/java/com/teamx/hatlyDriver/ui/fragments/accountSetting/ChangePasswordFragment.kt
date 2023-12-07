package com.teamx.hatlyDriver.ui.fragments.accountSetting

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentChangePasswordBinding
import com.teamx.hatlyDriver.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class ChangePasswordFragment :
    BaseFragment<FragmentChangePasswordBinding, EditProfileViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_change_password
    override val viewModel: Class<EditProfileViewModel>
        get() = EditProfileViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


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

        mViewDataBinding.btnSignup.setOnClickListener {
            mViewModel.changePassword(createJson())
        }

        mViewDataBinding.imgBack.setOnClickListener {
            popUpStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            })

        if (!mViewModel.changePasswordResponse.hasActiveObservers()) {
            mViewModel.changePasswordResponse.observe(requireActivity()) {
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
                                if (isAdded) {
                                    popUpStack()
                                    mViewDataBinding.root.snackbar(data.message)
                                }
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }


    }


    private fun createJson(): JsonObject {
        val params = JsonObject()
        try {
            if (mViewDataBinding.etName.text!!.isNotEmpty()) {
                params.addProperty("oldPassword", mViewDataBinding.etName.text.toString())
            }

            if (mViewDataBinding.etEmail.text!!.isNotEmpty()) {
                params.addProperty("password", mViewDataBinding.etEmail.text.toString())
            }

            if (mViewDataBinding.etPhone.text!!.isNotEmpty()) {
                params.addProperty("confirmPassword", mViewDataBinding.etPhone.text.toString())
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }


}
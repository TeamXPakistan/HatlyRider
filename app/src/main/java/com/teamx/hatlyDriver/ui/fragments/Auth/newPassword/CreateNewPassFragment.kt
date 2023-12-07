package com.teamx.hatlyDriver.ui.fragments.Auth.newPassword

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentNewPassBinding
import com.teamx.hatlyDriver.utils.DialogHelperClass
import com.teamx.hatlyDriver.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

@AndroidEntryPoint
class CreateNewPassFragment : BaseFragment<FragmentNewPassBinding, CreateNewPassViewModel>(),
    DialogHelperClass.Companion.ChangePasswordDialog {

    override val layoutId: Int
        get() = R.layout.fragment_new_pass
    override val viewModel: Class<CreateNewPassViewModel>
        get() = CreateNewPassViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var userNew: String? = null
    private var userConfirmPass: String? = null

    private var phone: String? = ""
    private var uniqueId: String? = ""

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


        mViewDataBinding.btnSave.setOnClickListener {
            if (isValidate()) {
                initialization()
                mViewModel.updatePass(createParams())
            }
        }

        val bundle = arguments
        phone = bundle?.getString("phone")
        uniqueId = bundle?.getString("uniqueId")
        Log.d("createParams", "onViewCreated phone: $phone")

        if (!mViewModel.updateResponse.hasActiveObservers()) {
            mViewModel.updateResponse.observe(requireActivity()) {
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


                            DialogHelperClass.changePasswordDialog(requireActivity(), this) {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    try {
                                        // Perform the work here, e.g., saveUserToken
                                        dataStoreProvider.saveUserToken(data.token)
                                    } catch (e: Exception) {
                                        // Handle exceptions as needed
                                    }
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

    private fun initialization() {
        userNew = mViewDataBinding.etPass.text.toString().trim()
        userConfirmPass = mViewDataBinding.etCnfrmPass.text.toString().trim()
    }

    private fun createParams(): JsonObject {
        val params = JsonObject()
        try {
            params.addProperty("contact", phone)
            params.addProperty("password", userConfirmPass.toString())
            params.addProperty("uniqueId", uniqueId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    private fun isValidate(): Boolean {
        if (mViewDataBinding.etPass.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter password")
            return false
        }
        if (mViewDataBinding.etCnfrmPass.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter confirm password")
            return false
        }
        if (mViewDataBinding.etPass.text.toString()
                .trim() != mViewDataBinding.etCnfrmPass.text.toString().trim()
        ) {
            mViewDataBinding.root.snackbar("Password not matched")
            return false
        }
        return true
    }

    override fun onLoginButton() {
        navController = Navigation.findNavController(
            requireActivity(),
            R.id.nav_host_fragment
        )
        navController.navigate(R.id.logInFragment, null, options)
    }

}
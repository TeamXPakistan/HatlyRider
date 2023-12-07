package com.teamx.hatlyDriver.ui.fragments.Dashboard.Profile

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.squareup.picasso.Picasso
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentProfileBinding
import com.teamx.hatlyDriver.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_profile
    override val viewModel: Class<ProfileViewModel>
        get() = ProfileViewModel::class.java
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


        mViewDataBinding.imgBack.setOnClickListener {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.homeFragment, arguments, options)
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController =
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    navController.navigate(R.id.homeFragment, arguments, options)


                }
            })

        mViewDataBinding.btnLogout.setOnClickListener {
            mViewModel.logout()
            if (!mViewModel.logoutResponse.hasActiveObservers()) {
                mViewModel.logoutResponse.observe(requireActivity()) {
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
                                CoroutineScope(Dispatchers.Main).launch {
                                    dataStoreProvider.removeAll()
                                }

                                navController = Navigation.findNavController(
                                    requireActivity(),
                                    R.id.nav_host_fragment
                                )
                                navController.navigate(R.id.logInFragment, arguments, options)
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

        mViewDataBinding.btnAccountSettings.setOnClickListener {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.editProfileFragment, arguments, options)
        }

        mViewDataBinding.btnHelpCentre.setOnClickListener {
            showToast("Coming Soon")
        }
        mViewDataBinding.btnPrivacy.setOnClickListener {
            showToast("Coming Soon")
        }
        mViewDataBinding.btnTerms.setOnClickListener {
            showToast("Coming Soon")
        }
        mViewDataBinding.btnContactUs.setOnClickListener {
            showToast("Coming Soon")
        }

        mViewDataBinding.btnWallet.setOnClickListener {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.walletFragment, arguments, options)
        }

        mViewDataBinding.imgNotification.setOnClickListener {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.notificaitonFragment, arguments, options)
        }


        mViewModel.me()
        if (!mViewModel.meResponse.hasActiveObservers()) {
            mViewModel.meResponse.observe(requireActivity()) {
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
                            mViewDataBinding.userName.text = try {
                                data.name.toString()
                            } catch (e: Exception) {
                                ""
                            }
                            mViewDataBinding.userEmail.text = try {
                                data.contact.toString()
                            } catch (e: Exception) {
                                ""
                            }
                            try {

                                Picasso.get().load(data?.profileImage).resize(500, 500)
                                    .into(mViewDataBinding.profilePicture)
                            } catch (e: Exception) {

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

}
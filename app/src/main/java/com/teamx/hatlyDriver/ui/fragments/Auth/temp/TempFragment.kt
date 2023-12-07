package com.teamx.hatlyDriver.ui.fragments.Auth.temp

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.constants.NetworkCallPoints.Companion.TOKENER
import com.teamx.hatlyDriver.databinding.FragmentTempBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TempFragment : BaseFragment<FragmentTempBinding, TempViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_temp
    override val viewModel: Class<TempViewModel>
        get() = TempViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


        mViewDataBinding.login.setOnClickListener {
            loginListener()
        }
        mViewDataBinding.register.setOnClickListener {
            registerListener()
        }



        CoroutineScope(Dispatchers.Main).launch {

            dataStoreProvider.token.collect {
                Timber.tag("TAG").d("CoroutineScope ${it}")

                val token = it

                TOKENER = token.toString()

                if (token.isNullOrBlank()) {

                    mViewDataBinding.login.setOnClickListener {
                        loginListener()
                    }
                    mViewDataBinding.register.setOnClickListener {
                        registerListener()
                    }
                } else {
                    navController =
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    navController.navigate(R.id.homeFragment, arguments, options)

//                    if (mViewModel.meResponse.isInitialized) {
//                        Log.d("123123", "onViewCreated2222:true ")
//                    } else {
//                        Log.d("123123", "onViewCreated2222:false ")
//
//                    }
//                    var bundle = arguments
//
//
//                    mViewModel.me()
//
//                    if (!mViewModel.meResponse.hasActiveObservers()) {
//
//                        if (isAdded) {
//                            mViewModel.meResponse.observe(
//                                requireActivity(),
//                                Observer {
//                                    when (it.status) {
//                                        Resource.Status.LOADING -> {
//                                            loadingDialog.show()
//                                            Timber.tag("343434").d("start")
//                                        }
//                                        Resource.Status.AUTH -> {
//                                            loadingDialog.dismiss()
//                                            onToSignUpPage()
//                                        }
//
//                                        Resource.Status.SUCCESS -> {
//                                            loadingDialog.dismiss()
//                                            it.data.let {
//                                                it?.let {
////
//                                                    if (bundle != null) {
//
//                                                        bundle?.putString(
//                                                            "userimg",
//                                                            "${it.profileImage}"
//                                                        ).toString()
//                                                        bundle?.putString(
//                                                            "username",
//                                                            "${it.name}"
//                                                        ).toString()
//                                                    } else {
//                                                        bundle = Bundle()
//
//                                                        bundle?.putString(
//                                                            "userimg",
//                                                            "${it.profileImage}"
//                                                        ).toString()
//                                                        bundle?.putString(
//                                                            "username",
//                                                            "${it.name}"
//                                                        ).toString()
//                                                    }
//
//
//                                                    navController =
//                                                        Navigation.findNavController(
//                                                            requireActivity(),
//                                                            R.id.nav_host_fragment
//                                                        )
//                                                    navController.navigate(
//                                                        R.id.homeFragment,
//                                                        bundle,
//                                                        options
//                                                    )
//
//
////                                                    if (isAdded) {
////
////                                                        if (ActivityCompat.checkSelfPermission(
////                                                                requireContext(),
////                                                                Manifest.permission.ACCESS_FINE_LOCATION
////                                                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
////                                                                requireContext(),
////                                                                Manifest.permission.ACCESS_COARSE_LOCATION
////                                                            ) != PackageManager.PERMISSION_GRANTED
////                                                        ) {
////                                                          /*  navController =
////                                                                Navigation.findNavController(
////                                                                    requireActivity(),
////                                                                    R.id.nav_host_fragment
////                                                                )
////                                                            navController.navigate(
////                                                                R.id.allowLocationFragment,
////                                                                bundle,
////                                                                options
////                                                            )*/
////
////                                                            DialogHelperClass.confirmLocation(
////                                                                requireContext(), this@TempFragment, true
////                                                            )
////
////                                                        } else {
////                                                            navController =
////                                                                Navigation.findNavController(
////                                                                    requireActivity(),
////                                                                    R.id.nav_host_fragment
////                                                                )
////                                                            navController.navigate(
////                                                                R.id.dashboard,
////                                                                bundle,
////                                                                options
////                                                            )
////                                                        }
////
////
////                                                    }
//
//
//                                                }
//
//
//                                            }
//                                        }
//
//                                        Resource.Status.ERROR -> {
//                                            loadingDialog.dismiss()
//                                            Timber.tag("343434").d("end")
//
//                                            DialogHelperClass.errorDialog(
//                                                requireContext(),
//                                                it.message!!
//                                            )
//                                        }
//                                    }
//                                    if (isAdded) {
////                mViewModel.meResponse.removeObservers(viewLifecycleOwner)
//                                    }
//                                })
//                        }
//                    }



                }
            }

        }

//        askNotificationPermission()


//        Handler(Looper.getMainLooper()).postDelayed({
//            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
//            navController.navigate(R.id.logInFragment, null, options)
//
//        }, 2000)


    }


 /*   private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {


        } else {

        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {



                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

            } else {
                // Directly ask for t
                //
                //
                // he permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }*/


    private fun loginListener() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.logInFragment, null, options)
    }

    private fun registerListener() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.signupFragment, null, options)
    }

    private val PERMISSION_REQUEST_CODE = 123

//    private fun requestPermission() {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
//                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            /* RiderSocketClass.connectRider(
//                 "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZGVudGlmaWNhdGlvbiI6eyJpdiI6IjZiNjQ3NTMzNjkzODM3NjM2ODMyNmIzOTM1MzczODY0IiwiZW5jcnlwdGVkRGF0YSI6IjM4OTFhZWVmYjBlZDgwZmU2ZDY3OWEwYWQzY2IzNGQyZWM3MDA4MDFjZWNiZDY0NDk4ZWZlOWEwZjMxMDNkMjEifSwidW5pcXVlSWQiOiI0OGZiMTU2OTg2ZDNkM2IzYmQ3ZTIyMjM0MmY0YTQiLCJpYXQiOjE2OTc0NzA4MzksImV4cCI6MTAzMzc0NzA4Mzl9.V-hG2OFgmRy8D0PQCICXNHp6GeqUpAXq09hqU8OXeco",
//                 originLatitude,
//                 originLongitude
//             )*/
//
//            // Show an explanation to the user *asynchronously*
//            // why the permission is needed and why the user should grant it
//
//            requestPermissions(
//                arrayOf(
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ), PERMISSION_REQUEST_CODE
//            )
//        } else {
//            // Permission has already been granted
//            RiderSocketClass.connectRider(
//                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZGVudGlmaWNhdGlvbiI6eyJpdiI6IjZiNjQ3NTMzNjkzODM3NjM2ODMyNmIzOTM1MzczODY0IiwiZW5jcnlwdGVkRGF0YSI6IjM4OTFhZWVmYjBlZDgwZmU2ZDY3OWEwYWQzY2IzNGQyZWM3MDA4MDFjZWNiZDY0NDk4ZWZlOWEwZjMxMDNkMjEifSwidW5pcXVlSWQiOiI0OGZiMTU2OTg2ZDNkM2IzYmQ3ZTIyMjM0MmY0YTQiLCJpYXQiOjE2OTc0NzA4MzksImV4cCI6MTAzMzc0NzA4Mzl9.V-hG2OFgmRy8D0PQCICXNHp6GeqUpAXq09hqU8OXeco",
//                originLatitude,
//                originLongitude,
//                this
//            )
//
//            /* Firebase.initialize(requireContext())
//             FirebaseApp.initializeApp(requireContext())
//
//             if (!mViewModel.fcmResponse.hasActiveObservers()) {
//                 askNotificationPermission()
//             }*/
//
//            /*DialogHelperClass.confirmLocation(
//                requireContext(), this@HomeFragment, true*/
//
//
////            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
////            navController.navigate(R.id.dashboard, null, options)
//        }
//    }


  /*  private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(), Manifest.permission.CAMERA
                )
            ) {
                // Show an explanation to the user *asynchronously*
                // why the permission is needed and why the user should grant it
            }
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission has already been granted
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.tempFragment, arguments, options)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.tempFragment, arguments, options)
        } else {
            val snackbar = Snackbar.make(
                mViewDataBinding.root, "Permission required to proceed..", Snackbar.LENGTH_SHORT
            )
            snackbar.setAction("Settings") {
                //
                Timber.tag("TAG").d("ScankonRequestPermissionsResult: ")*//*        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", requireActivity().packageName, null)
                        intent.data = uri
                        requireActivity().startActivity(intent)*//*
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context?.packageName, null)
                intent.data = uri
                startActivity(intent)
                //
//                snackbar.dismiss()
            }
            snackbar.show()

            // Permission was denied. Handle this however is appropriate for your app.
        }
    }

    override fun onConfirmLocation() {
        requestPermission()
    }*/
}

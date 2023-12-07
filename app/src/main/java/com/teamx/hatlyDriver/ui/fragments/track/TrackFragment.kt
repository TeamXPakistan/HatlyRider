package com.teamx.hatlyDriver.ui.fragments.track

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.JsonObject
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.TravelMode
import com.squareup.picasso.Picasso
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.constants.NetworkCallPoints
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentTrackBinding
import com.teamx.hatlyDriver.ui.fragments.Dashboard.home.HomeFragment
import com.teamx.hatlyDriver.ui.fragments.chat.socket.RiderSocketClass
import com.teamx.hatlyDriver.ui.fragments.chat.socket.TrackSocketClass
import com.teamx.hatlyDriver.ui.fragments.topUp.TopUpModel
import com.teamx.hatlyDriver.utils.DialogHelperClass
import com.teamx.hatlyDriver.utils.LocationPermission
import com.teamx.hatlyDriver.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import timber.log.Timber

@AndroidEntryPoint
class TrackFragment : BaseFragment<FragmentTrackBinding, TopUpModel>(), OnMapReadyCallback,
    android.location.LocationListener {

    override val layoutId: Int
        get() = R.layout.fragment_track
    override val viewModel: Class<TopUpModel>
        get() = TopUpModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    var mapFragment: SupportMapFragment? = null
    private lateinit var googleMap: GoogleMap


    var locationCallback: LocationCallback? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>


    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var locationRequest: LocationRequest? = null


    // Coordinates of a park nearby
    private var destinationLatitude: Double = 0.0
    private var destinationLongitude: Double = 0.0


    private var originLatitude: Double = 0.0
    private var originLongitude: Double = 0.0

    private var destinitionLatitude: Double = 0.0
    private var destinitionLongitude: Double = 0.0

    lateinit var bundle: Bundle

    lateinit var handler: Handler
    lateinit var runnable: Runnable
    lateinit var reqid: String
    lateinit var id: String

    @RequiresApi(Build.VERSION_CODES.S)
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

        handler = Handler()

        orderDetailsApiCall()

        bundle = Bundle()


        mViewDataBinding.bottomSheetLayout.imgChat.setOnClickListener {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.chatFragment, bundle, options)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController =
                        Navigation.findNavController(
                            requireActivity(),
                            R.id.nav_host_fragment
                        )
                    navController.navigate(
                        R.id.homeFragment,
                        arguments,
                        options
                    )


                }
            })


        try {
            requestPermission()
        } catch (e: Exception) {
        }

        mViewDataBinding.constraintLayout.setOnClickListener {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.homeFragment, arguments, options)
            RiderSocketClass.disconnect()

        }

        mViewDataBinding.bottomSheetLayout.btnComplete.setOnClickListener {
            val params = JsonObject()
            try {
                params.addProperty("status", "delivered")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            mViewModel.pickedDispatchOrder(id, params)
        }


        mViewModel.pickedDispatchOrderResponse.observe(requireActivity(), Observer {
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
                        TrackSocketClass.disconnect()
                        navController = Navigation.findNavController(
                            requireActivity(),
                            R.id.nav_host_fragment
                        )
                        navController.navigate(R.id.homeFragment, null, options)

                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    DialogHelperClass.errorDialog(requireContext(), it.message!!)
                }
            }
        })

        bottomSheetBehavior =
            BottomSheetBehavior.from(mViewDataBinding.bottomSheetLayout.bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Log.d("bottomSheetBehavior", "onStateChanged: STATE_EXPANDED")

                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Log.d("bottomSheetBehavior", "onStateChanged: STATE_COLLAPSED")

                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        Log.d("bottomSheetBehavior", "onStateChanged: STATE_DRAGGING")
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.d("bottomSheetBehavior", "onStateChanged: STATE_HIDDEN")
                    }
                }
            }
        })

        val state =
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) BottomSheetBehavior.STATE_COLLAPSED
            else BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.state = state


        mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)


        getDeviceLocation()

        runnable = Runnable {
            getDeviceLocation()
            Log.d("runnableIs", "Runnable: ")
            TrackSocketClass.updateRide(originLatitude, originLongitude)
            createPollyLine(
                LatLng(originLatitude, originLongitude),
                LatLng(destinitionLatitude, destinitionLongitude)
            )
            handler.postDelayed(runnable, 3000)
        }


    }


//    private fun checkLocationIsEnable(): Boolean {
//        val locationManager =
//            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER
//        )
//    }


    private val PERMISSION_REQUEST_CODE = 123

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            /* RiderSocketClass.connectRider(
                 "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZGVudGlmaWNhdGlvbiI6eyJpdiI6IjZiNjQ3NTMzNjkzODM3NjM2ODMyNmIzOTM1MzczODY0IiwiZW5jcnlwdGVkRGF0YSI6IjM4OTFhZWVmYjBlZDgwZmU2ZDY3OWEwYWQzY2IzNGQyZWM3MDA4MDFjZWNiZDY0NDk4ZWZlOWEwZjMxMDNkMjEifSwidW5pcXVlSWQiOiI0OGZiMTU2OTg2ZDNkM2IzYmQ3ZTIyMjM0MmY0YTQiLCJpYXQiOjE2OTc0NzA4MzksImV4cCI6MTAzMzc0NzA4Mzl9.V-hG2OFgmRy8D0PQCICXNHp6GeqUpAXq09hqU8OXeco",
                 originLatitude,
                 originLongitude
             )*/

            // Show an explanation to the user *asynchronously*
            // why the permission is needed and why the user should grant it

            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission has already been granted
            TrackSocketClass.connectRiderTrack(
                NetworkCallPoints.TOKENER,
                id
            )

            /* Firebase.initialize(requireContext())
             FirebaseApp.initializeApp(requireContext())

             if (!mViewModel.fcmResponse.hasActiveObservers()) {
                 askNotificationPermission()
             }*/

            /*DialogHelperClass.confirmLocation(
                requireContext(), this@HomeFragment, true*/


//            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
//            navController.navigate(R.id.dashboard, null, options)
        }
    }


    fun orderDetailsApiCall() {
        try {
            mViewModel.getPastOrders(1, 10, "accepted")
        } catch (e: Exception) {

        }

        if (!mViewModel.getPastOrdersResponse.hasActiveObservers()) {
            mViewModel.getPastOrdersResponse.observe(requireActivity()) {
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
                            try {

                                destinitionLatitude = data.docs[0].dropOff.lat
                                destinitionLongitude = data.docs[0].dropOff.lng

                                reqid = data.docs[0].requestId
                                id = data.docs[0]._id

                                TrackSocketClass.connectRiderTrack(
                                    NetworkCallPoints.TOKENER,
                                    reqid
                                )

                                handler.postDelayed(runnable, 3000)

                                bundle.putString("orderId", data.docs[0].requestId)

                                mViewDataBinding.bottomSheetLayout.textView23.text =
                                    data.docs[0].shop.name
                                mViewDataBinding.bottomSheetLayout.textView24.text =
                                    data.docs[0].shop.address.streetAddress
                                mViewDataBinding.bottomSheetLayout.ratingBar.rating =
                                    data.docs[0].shop.ratting.toFloat()
                                Picasso.get().load(data.docs[0].shop.image)
                                    .into(mViewDataBinding.bottomSheetLayout.imgIcon)

                                try {
                                    Picasso.get().load(data.docs[0].orders.customer.profileImage)
                                        .into(mViewDataBinding.bottomSheetLayout.imgAvatar)

                                } catch (e: Exception) {

                                }


                                Picasso.get().load(data.docs[0].orders.products[0].image)
                                    .into(mViewDataBinding.bottomSheetLayout.imgIcon)


                                val address1 = data.docs[0].dropOff.address
                                val address2 = data.docs[0].pickup.formattedAddress

                                val trimmedAddress1 = address1.substringBefore("\n")
                                val trimmedAddress2 = address2.substringBefore("\n")


                                mViewDataBinding.bottomSheetLayout.textView33.text =
                                    trimmedAddress1

                                mViewDataBinding.bottomSheetLayout.textView24.text =
                                    trimmedAddress2


                                mViewDataBinding.bottomSheetLayout.textView27.text =
                                    data.docs[0].orders.products[0].productName
                                mViewDataBinding.bottomSheetLayout.textView29.text =
                                    data.docs[0].orders.products[0].prize.toString()

                                mViewDataBinding.bottomSheetLayout.textView28.text =
                                    data.docs[0].orders.products[0].prize.toString() + " AED"
                                mViewDataBinding.bottomSheetLayout.textView31.text =
                                    data.docs[0].orders.subTotal.toString()
                                mViewDataBinding.bottomSheetLayout.textView32.text =
                                    data.docs[0].orders.customer.name
                                mViewDataBinding.bottomSheetLayout.textView35.text =
                                    data.docs[0].orders.specialNote
                            } catch (e: Exception) {

                            }


                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getPastOrdersResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }



        try {
            mViewModel.getPastParcels(1, 10, "accepted")
        } catch (e: Exception) {

        }

        if (!mViewModel.getPastParcelsResponse.hasActiveObservers()) {
            mViewModel.getPastParcelsResponse.observe(requireActivity()) {
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
                            try {
                                mViewDataBinding.bottomSheetLayout.textView27.text =
                                    data.docs[0].parcel.details.item


                              destinitionLatitude = data.docs[0].parcel.senderId.coordinates.lat
                                destinitionLongitude = data.docs[0].parcel.senderId.coordinates.lng
                                reqid = data.docs[0].requestId
                                id = data.docs[0]._id

                                TrackSocketClass.connectRiderTrack(
                                    NetworkCallPoints.TOKENER,
                                    reqid
                                )

                                handler.postDelayed(runnable, 3000)

                                bundle.putString("orderId", data.docs[0].requestId)

                               mViewDataBinding.bottomSheetLayout.layout1.visibility = View.GONE


                      /*          try {
                                    Picasso.get().load(data.docs[0].orders.customer.profileImage)
                                        .into(mViewDataBinding.bottomSheetLayout.imgAvatar)

                                } catch (e: Exception) {

                                }
*/



                                val address1 = data.docs[0].parcel.senderLocation.location.address
                                val address2 = data.docs[0].parcel.receiverLocation.location.address

                                val trimmedAddress1 = address1.substringBefore("\n")
                                val trimmedAddress2 = address2.substringBefore("\n")


                                mViewDataBinding.bottomSheetLayout.textView33.text =
                                    trimmedAddress1

                                mViewDataBinding.bottomSheetLayout.textView24.text =
                                    trimmedAddress2

                                mViewDataBinding.bottomSheetLayout.textView25.text = "Parcel Summery"


                                mViewDataBinding.bottomSheetLayout.textView27.text =
                                    data.docs[0].parcel.details.item
                                mViewDataBinding.bottomSheetLayout.textView29.text =
                                    data.docs[0].parcel.fare.toString()

                                mViewDataBinding.bottomSheetLayout.textView28.text =
                                    data.docs[0].parcel.fare.toString() + " AED"

                                mViewDataBinding.bottomSheetLayout.textView31.text =
                                    data.docs[0].parcel.fare.toString()

                                mViewDataBinding.bottomSheetLayout.textView32.text =
                                    data.docs[0].parcel.senderId.name

//                                mViewDataBinding.bottomSheetLayout.textView35.text =
//                                    data.docs[0].orders.specialNote

                            } catch (e: Exception) {

                            }


                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getPastParcelsResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }





    }

    override fun onMapReady(p0: GoogleMap) {
        if (LocationPermission.requestPermission(requireActivity())) {
            googleMap = p0

//            googleMap.uiSettings.isZoomControlsEnabled = false
//            googleMap.uiSettings.isScrollGesturesEnabled = false
//            googleMap.uiSettings.isRotateGesturesEnabled = false
//            googleMap.isMyLocationEnabled = false
//            googleMap.uiSettings.isTiltGesturesEnabled = false
//            googleMap.uiSettings.isZoomGesturesEnabled = false
//            googleMap.uiSettings.isMapToolbarEnabled = false

//            val location = LatLng(24.90141311636262, 67.1151442708337) // San Francisco coordinates
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))

//            googleMap.clear()
//            createPollyLine()

        } else {
            if (isAdded) {
                mViewDataBinding.root.snackbar("Allow location")
            }
        }
    }


    private fun createPollyLine(origin: LatLng, destination: LatLng) {

//        val destination = LatLng(24.897369355794208, 67.07753405615058)
//        val origin = LatLng(24.90125984648241, 67.1152140082674)

        // Move the camera to the origin
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 12f))

        // Create a GeoApiContext with your API key
        val context =
            GeoApiContext.Builder().apiKey("AIzaSyAnLo0ejCEMH_cPgZaokWej4UdgyIIy5HI").build()

        // Request directions
        val directions = DirectionsApi.newRequest(context)
            .origin(com.google.maps.model.LatLng(origin.latitude, origin.longitude))
            .destination(com.google.maps.model.LatLng(destination.latitude, destination.longitude))
            .mode(TravelMode.DRIVING) // You can use other modes like walking, bicycling, etc.
            .await()

        try {

            // Convert Google Maps Directions API LatLng to Google Maps Android API LatLng
            val polyline = directions.routes[0].overviewPolyline.decodePath()
                .map { LatLng(it.lat, it.lng) }

            // Create a PolylineOptions and add the polyline to the map
            val polylineOptions = PolylineOptions()
                .addAll(polyline)
                .color(requireActivity().getColor(R.color.red))
                .width(10f) // Line width

            googleMap.clear()
            googleMap.addPolyline(polylineOptions)
            animateCameraAlongPolyline(polyline)
        } catch (e: Exception) {

        }

    }


    private fun animateCameraAlongPolyline(polyline: List<LatLng>) {

        googleMap.setOnMapLoadedCallback {
            val startPosition = polyline.first()


            val endPosition = polyline.last()

//        val centerPosition = LatLng(
//            (startPosition.latitude + endPosition.latitude) / 2,
//            (startPosition.longitude + endPosition.longitude) / 2
//        )

            // Calculate appropriate zoom level to fit the entire polyline
            /*val bounds = LatLngBounds.builder()
                .include(startPosition)
                .include(endPosition)
                .build()

            val padding = 100 // Padding in pixels*/

            val builder = LatLngBounds.builder()
            for (point in polyline) {
                builder.include(point)
            }
            val bounds = builder.build()


// Step 2: Adjust Camera Position


            /*      // Animate the camera to fit the bounds and center the polyline
                  val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                  googleMap.moveCamera(cameraUpdate)*/


            val padding = 100 // Adjust this value as needed

            googleMap.addMarker(MarkerOptions().position(startPosition).title("Start Marker"))
            val marker = googleMap.addMarker(
                MarkerOptions().position(endPosition)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.delivery_man))
            )


//            googleMap.addMarker(MarkerOptions().position(endPosition).title("End Marker"))

            if (marker != null) {
                animateMarker(marker, endPosition, false)
            }

            // Animate the camera to fit the bounds and center the polyline
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            googleMap.moveCamera(cameraUpdate)


            // Animate the camera to the center position with an appropriate zoom level
//        googleMap.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(centerPosition, 10f)
//        )
        }


    }


    private fun animateMarker(marker: Marker, toPosition: LatLng, hideMarker: Boolean) {
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val proj: Projection = googleMap.projection
        val startPoint = proj.toScreenLocation(marker.position)
        val startLatLng = proj.fromScreenLocation(startPoint)
        val duration: Long = 500
        val interpolator: LinearInterpolator = LinearInterpolator()
        handler.post(object : Runnable {
            override fun run() {
                val elapsed = SystemClock.uptimeMillis() - start
                val t: Float = interpolator.getInterpolation(elapsed.toFloat() / duration)
                val lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude
                val lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude
                marker.position = LatLng(lat, lng)
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16)
                } else {
                    marker.isVisible = !hideMarker
                }
            }
        })
    }


    override fun onLocationChanged(p0: Location) {
    }

    var locationPermissionGranted = true

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {

                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        Timber.tag("TAG")
                            .d("onCreate:latitude${it.latitude}longitude${it.longitude} ")
                    }
                }
                locationRequest = LocationRequest()
                locationRequest?.interval = 10
                locationRequest?.fastestInterval = 10
                locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                fusedLocationClient.requestLocationUpdates(
                    locationRequest!!, object : LocationCallback() {

                    }, Looper.getMainLooper()
                )

                val locationResult = fusedLocationClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        val lastKnownLocation = task.result
                        if (lastKnownLocation != null) {

                            originLatitude = lastKnownLocation.latitude
                            originLongitude = lastKnownLocation.longitude


                            Timber.tag("lastKnownLocation").d(
                                "Current location is . Using defaults. ${lastKnownLocation.latitude}  ${lastKnownLocation.longitude}"
                            )


                        }
                    } else {
                        Timber.tag("TAG").d("Current location is null. Using defaults.")
                        Timber.tag("TAG").d("Exception:   ${task.exception}")
                        HomeFragment.mMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }


        } catch (e: SecurityException) {
            Timber.tag("Exception: %s").e(e, e.message)
        }
    }


}
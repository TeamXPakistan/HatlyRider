package com.teamx.hatlyDriver.baseclasses

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.teamx.hatlyDriver.MainApplication
import pub.devrel.easypermissions.EasyPermissions


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    private lateinit var mViewDataBinding: T
    protected lateinit var mViewModel: V

    private val SOME_PERMISSION = 0

    /**
     * viewModel variable that will get value from activity which it will implement this
     * we will use this variable viewModel to bind with view through databinding
     */
    abstract val viewModel: Class<V>

    /**
     * layoutId variable to get layout value from activity which will implement this layoutId
     * we will use this layoutId for databinding
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * bindingVariable which will bind with view
     */

    abstract val bindingVariable: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databindingWithViewModel()

//        checkAndAskLocationPermission()

    }

    public fun checkAndAskLocationPermission() {
        if (EasyPermissions.hasPermissions(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            /// Already have permission, do the thing
            allowPermissionListener?.onPermissionAllow(true)
        } else {
            EasyPermissions.requestPermissions(
                this,
                "We need to access",
                SOME_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(MainApplication.localeManager!!.setLocale(newBase!!))
    }


    /**
     * Function to perform databinding and attaching viewmodel with view
     */
    private fun databindingWithViewModel() {
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mViewModel = ViewModelProvider(this).get(viewModel)
        mViewDataBinding.setVariable(bindingVariable, mViewModel)
        mViewDataBinding.executePendingBindings()

    }

    /**
     * OnAllowPermissionListener instance
     */
    var allowPermissionListener: OnAllowPermissionListener? = null


    interface OnAllowPermissionListener {
        fun onPermissionAllow(allAllow: Boolean = false)
    }

}


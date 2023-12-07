package com.teamx.hatlyDriver.ui.fragments.orders.special

import android.os.Bundle
import android.view.View
import androidx.navigation.navOptions
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.databinding.FragmentSpecialBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecialFragment : BaseFragment<FragmentSpecialBinding, SpecialViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_special
    override val viewModel: Class<SpecialViewModel>
        get() = SpecialViewModel::class.java
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




    }




}
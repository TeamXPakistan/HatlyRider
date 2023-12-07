package com.teamx.hatlyDriver.ui.fragments.orders

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.databinding.FragmentOrdersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class
OrdersFragment : BaseFragment<FragmentOrdersBinding, OrdersViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_orders
    override val viewModel: Class<OrdersViewModel>
        get() = OrdersViewModel::class.java
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
            navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment
                )
            navController.navigate(
                R.id.homeFragment,
                arguments,
                options
            )        }


        mViewDataBinding.imgNotification.setOnClickListener {
            navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment
                )
            navController.navigate(
                R.id.notificaitonFragment,
                arguments,
                options
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Handle the back button event here
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

        setupViewPager()
        setupTabLayout()


    }

    private fun setupTabLayout() {
        TabLayoutMediator(
            mViewDataBinding.tabLayout, mViewDataBinding.viewPager
        ) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = "Active"
                }

                1 -> {
                    tab.text = "Incoming"
                }

                2 -> {
                    tab.text = "Completed"
                }
            }

            Log.d("setupTabLayout", "setupTabLayout: $position")

//            {tab.text = "Tab " + (position + 1)}

        }.attach()
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(requireActivity(), 3)
        mViewDataBinding.viewPager.adapter = adapter
    }


}
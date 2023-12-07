package com.teamx.hatlyDriver.ui.fragments.parcel.Completed

import android.os.Bundle
import android.view.View
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.data.dataclasses.pastParcels.Doc
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentCompletedBinding
import com.teamx.hatlyDriver.ui.fragments.orders.Completed.CompletedViewModel
import com.teamx.hatlyDriver.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletedParcelFragment : BaseFragment<FragmentCompletedBinding, CompletedViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_completed
    override val viewModel: Class<CompletedViewModel>
        get() = CompletedViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    lateinit var pastOrderAdapter: CompleteParcelAdapter
    lateinit var pastOrderArrayList: ArrayList<Doc>

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


        mViewModel.getPastParcels(1, 5,"delivered")

        mViewModel.getPastParcelsResponse.observe(requireActivity()) {
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
                        data.docs.forEach {
                            pastOrderArrayList.add(it)
                        }

                        pastOrderAdapter.notifyDataSetChanged()


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

        OrderRecyclerview()
    }

    private fun OrderRecyclerview() {
        pastOrderArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.pastRecyclerView.layoutManager = linearLayoutManager

        pastOrderAdapter = CompleteParcelAdapter(pastOrderArrayList)
        mViewDataBinding.pastRecyclerView.adapter = pastOrderAdapter

    }

}
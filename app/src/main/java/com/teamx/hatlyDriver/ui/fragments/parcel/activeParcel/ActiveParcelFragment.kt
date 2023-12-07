package com.teamx.hatlyDriver.ui.fragments.parcel.activeParcel

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.data.dataclasses.pastParcels.Doc
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentActiveParcelBinding
import com.teamx.hatlyDriver.ui.fragments.orders.active.ActiveViewModel
import com.teamx.hatlyDriver.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class ActiveParcelFragment : BaseFragment<FragmentActiveParcelBinding, ActiveViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_active_parcel
    override val viewModel: Class<ActiveViewModel>
        get() = ActiveViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    //    lateinit var productAdapter: ActiveAdapter
    lateinit var activeOrderAdapter: ActiveParcelAdapter

    //    lateinit var productArrayList: ArrayList<String>
    lateinit var activeOrderArrayList: ArrayList<Doc>

    var id: String = ""


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
                                id = data.docs[0]._id
                                if (data.docs[0].status == "picked") {
                                    mViewDataBinding.txtOrderConfoirm.visibility = View.GONE
                                    mViewDataBinding.btnTrack.visibility = View.VISIBLE
                                }
                                mViewDataBinding.txtOrderConfoirm.visibility = View.VISIBLE

                                Log.d("TAG", "onViewCreated121212: $id")
                            } catch (e: Exception) {
                            }
                            data.docs.forEach {
                                activeOrderArrayList.add(it)
                            }

                            activeOrderAdapter.notifyDataSetChanged()


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

        mViewDataBinding.btnTrack.setOnClickListener {
            navController = Navigation.findNavController(
                requireActivity(),
                R.id.nav_host_fragment
            )
            navController.navigate(R.id.trackFragment, null, options)
        }

        mViewDataBinding.txtOrderConfoirm.setOnClickListener {

            val params = JsonObject()
            try {
                params.addProperty("status", "picked")
            } catch (e: JSONException) {
                e.printStackTrace()
            }


            mViewModel.pickedDispatchOrder(id, params)

            mViewModel.pickedDispatchOrderResponse.observe(requireActivity(), Observer {
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
                            navController = Navigation.findNavController(
                                requireActivity(),
                                R.id.nav_host_fragment
                            )
                            navController.navigate(R.id.trackFragment, null, options)
                            showToast(data.message)
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
            })

        }



        ActiveParcelRecyclerview()


    }

    private fun ActiveParcelRecyclerview() {
        activeOrderArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.activeRecyclerView.layoutManager = linearLayoutManager

        activeOrderAdapter = ActiveParcelAdapter(activeOrderArrayList)
        mViewDataBinding.activeRecyclerView.adapter = activeOrderAdapter

    }


}
package com.teamx.hatlyDriver.ui.fragments.parcel.incomingParcel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.data.dataclasses.pastParcels.Doc
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentIncomingBinding
import com.teamx.hatlyDriver.ui.fragments.orders.Incoming.IncomingViewModel
import com.teamx.hatlyDriver.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class IncomingParcelFragment : BaseFragment<FragmentIncomingBinding, IncomingViewModel>(),
    DialogHelperClass.Companion.ReasonDialog,
    onAcceptRejectPar {

    override val layoutId: Int
        get() = R.layout.fragment_incoming
    override val viewModel: Class<IncomingViewModel>
        get() = IncomingViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var id: String


    lateinit var incomingOrderAdapter: IncomingParcelAdapter
    lateinit var incomingOrderArrayList: ArrayList<Doc>

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
            mViewModel.getPastParcels(1,10,"incoming")
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
                            data.docs.forEach {
                                incomingOrderArrayList.add(it)
                            }

                            incomingOrderAdapter.notifyDataSetChanged()


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



        IncomingOrderRecyclerview()
    }

    private fun IncomingOrderRecyclerview() {
        incomingOrderArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.activeRecyclerView.layoutManager = linearLayoutManager

        incomingOrderAdapter = IncomingParcelAdapter(incomingOrderArrayList, this)
        mViewDataBinding.activeRecyclerView.adapter = incomingOrderAdapter

    }

    override fun onAcceptClick(position: Int) {
        id = incomingOrderArrayList[position]._id

        val params = JsonObject()
        try {
            params.addProperty("status", "accepted")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mViewModel.acceptReject(id, params)

        mViewModel.acceptRejectResponse.observe(requireActivity(), Observer {
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
                        showToast(data.message)
                        incomingOrderAdapter.notifyDataSetChanged()
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    DialogHelperClass.errorDialog(requireContext(), it.message!!)
                }
            }
        })


    }

    override fun onRejectClick(position: Int) {


        DialogHelperClass.submitReason(
            requireContext(), this, true, "", ""
        )
    }

    override fun onSubmitClick(status: String, rejectionReason: String) {
        val params = JsonObject()
        try {
            params.addProperty("status", "rejected")
            params.addProperty("rejectionReason", rejectionReason)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mViewModel.acceptReject(id, params)

        mViewModel.acceptRejectResponse.observe(requireActivity(), Observer {
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


    override fun onCancelClick() {
        TODO("Not yet implemented")
    }


}
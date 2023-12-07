package com.teamx.hatlyDriver.ui.fragments.wallet

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentTransactionBinding
import com.teamx.hatlyDriver.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TranscationHistoryFragment : BaseFragment<FragmentTransactionBinding, WalletViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_transaction
    override val viewModel: Class<WalletViewModel>
        get() = WalletViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    var isScrolling = false
    var currentItems = 0
    var totalItems = 0
    var scrollOutItems = 0


    lateinit var transactionHistoryAdapter: TransactionAdapter
    lateinit var transactionHistoryArrayList: ArrayList<com.teamx.hatlyDriver.data.dataclasses.transactionHistory.Doc>

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

        mViewDataBinding.imgBack.setOnClickListener {
            popUpStack()
        }

        mViewModel.trancationHisotory(10, 1)
        if (!mViewModel.transactionHistoryResponse.hasActiveObservers()) {
            mViewModel.transactionHistoryResponse.observe(requireActivity()) {
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
                                transactionHistoryArrayList.add(it)
                            }

                            transactionHistoryAdapter.notifyDataSetChanged()
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }



        transactionHistoryArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recTrans.layoutManager = linearLayoutManager

        transactionHistoryAdapter = TransactionAdapter(transactionHistoryArrayList)
        mViewDataBinding.recTrans.adapter = transactionHistoryAdapter

        mViewDataBinding.recTrans.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                currentItems = linearLayoutManager.childCount
                totalItems = linearLayoutManager.itemCount
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition()

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
//                    fetchData()
                }
            }
        })


    }


}
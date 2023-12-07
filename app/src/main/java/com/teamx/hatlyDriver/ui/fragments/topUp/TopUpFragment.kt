package com.teamx.hatlyDriver.ui.fragments.topUp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.teamx.hatlyDriver.BR
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.baseclasses.BaseFragment
import com.teamx.hatlyDriver.data.remote.Resource
import com.teamx.hatlyDriver.databinding.FragmentTopUpBinding
import com.teamx.hatlyDriver.utils.DialogHelperClass
import com.teamx.hatlyDriver.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopUpFragment : BaseFragment<FragmentTopUpBinding, TopUpModel>(),
    DialogHelperClass.Companion.ContactUs {

    override val layoutId: Int
        get() = R.layout.fragment_top_up
    override val viewModel: Class<TopUpModel>
        get() = TopUpModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var paymentSheet: PaymentSheet

    private var paymentMethodid = ""

    private var amount = ""

    private var selectedPaymentMethod = PaymentMethod.STRIPE_SAVED_PAYMENT

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

        if (isAdded) {
            paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            val params = JsonObject()

            amount = mViewDataBinding.userEmail.text.toString()

            if (amount.isEmpty()) {
                mViewDataBinding.root.snackbar("Enter Amount")
                return@setOnClickListener
            }
            params.addProperty("amount", amount.toInt())
            when (selectedPaymentMethod) {
                PaymentMethod.CASH_ON_DELIVERY -> {
                }

                PaymentMethod.STRIPE_PAYMENT -> {

                }

                PaymentMethod.STRIPE_SAVED_PAYMENT -> {
                    params.addProperty("payment_method", paymentMethodid)
                }

                PaymentMethod.PAYPAL -> {
                }
            }
            mViewModel.topUpSaved(params)
        }

        mViewDataBinding.radioSelectedCard.setOnClickListener {
            mViewDataBinding.radio1.isChecked = true
            mViewDataBinding.radioAddNewCard.isChecked = false
            selectedPaymentMethod = PaymentMethod.STRIPE_SAVED_PAYMENT
        }

        mViewDataBinding.radioAddNewCard.setOnClickListener {
            mViewDataBinding.radio1.isChecked = false
            mViewDataBinding.radioAddNewCard.isChecked = true
            selectedPaymentMethod = PaymentMethod.STRIPE_PAYMENT
        }

        mViewDataBinding.textView121653.setOnClickListener {
            if (isAdded) {
//                findNavController().navigate(R.id.action_topUpFragment_to_paymentMethodFragment)
                navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                navController.navigate(R.id.paymentMethodFragment, arguments, options)
            }
        }

        mViewModel.credCards()

        mViewModel.credCardsResponse.observe(requireActivity()) {
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
                        mViewDataBinding.textView12155453.text = try {
                            "**** **** **** ${data.default!!.card.last4} | ${data.default.card.exp_month}/${data.default!!.card.exp_year}"
                        } catch (e: Exception) {
                            ""
                        }
                        if (data.default?.id?.isNotEmpty() == true) {
                            paymentMethodid = data.default.id
                        }
                        else{
                            paymentMethodid = ""
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }

        if (!mViewModel.topUpSavedResponse.hasActiveObservers()) {
            mViewModel.topUpSavedResponse.observe(requireActivity()) {
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
                            when (selectedPaymentMethod) {
                                PaymentMethod.CASH_ON_DELIVERY -> {
                                }

                                PaymentMethod.STRIPE_PAYMENT -> {
                                    showStripeSheet(data.clientSecret)
                                }

                                PaymentMethod.STRIPE_SAVED_PAYMENT -> {
                                    DialogHelperClass.wallettDialog(requireActivity(), amount, this)
                                }

                                PaymentMethod.PAYPAL -> {
                                }
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

    private fun showStripeSheet(clientSecret: String) {
        PaymentConfiguration.init(
            requireActivity().applicationContext,
//            stripPublicKey
            "pk_test_51LMwtTIXOwead2Sp6mZEM5tGaiZT363HLHm58hq7Wrip8KOH2Jj1U303ONw2DMd6oTGHP0uLiDw197LA0jauVeMG00HtE9n9nM"
        )

        paymentSheet.presentWithPaymentIntent(
            clientSecret, PaymentSheet.Configuration(
                merchantDisplayName = "HatlyDriver",
//                customer = customerConfig,
                // Set `allowsDelayedPaymentMethods` to true if your business
                // can handle payment methods that complete payment after a delay, like SEPA Debit and Sofort.
                allowsDelayedPaymentMethods = true
            )
        )
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Log.d("placeOrderResponse", "onPaymentSheetResult: Canceled")
            }

            is PaymentSheetResult.Failed -> {
                Log.d("placeOrderResponse", "onPaymentSheetResult: Failed")
            }

            is PaymentSheetResult.Completed -> {
                Log.d("placeOrderResponse", "onPaymentSheetResult: Completed")
                DialogHelperClass.wallettDialog(requireActivity(), amount, this)
//                if (isAdded) {
//                    findNavController().navigate(R.id.action_checkOutFragment_to_orderPlacedFragment)
//                }
            }
        }
    }

    override fun onBackToHome() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.homeFragment, arguments, options)
    }

    enum class PaymentMethod {
        CASH_ON_DELIVERY,
        STRIPE_PAYMENT,
        STRIPE_SAVED_PAYMENT,
        PAYPAL
    }

}
package com.teamx.hatlyDriver.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.teamx.hatlyDriver.R
import kotlinx.coroutines.Job

class DialogHelperClass {
    companion object {

        fun loadingDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_layout_loading)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            return dialog
        }


        interface DialogCallBackSignIn {
            fun onSignInClick1()
            fun onSignUpClick1()
        }

        fun signUpLoginDialog(context: Context, dialogCallBack: DialogCallBackSignIn): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.signup_signin_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val signIn = dialog.findViewById<TextView>(R.id.signIn)
            val signUp = dialog.findViewById<TextView>(R.id.signUp)

            signUp.setOnClickListener {
                dialogCallBack.onSignUpClick1()
                dialog.dismiss()
            }
            signIn.setOnClickListener {
                dialogCallBack.onSignInClick1()
                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            return dialog
        }


        interface ContactUs {
            fun onBackToHome()
        }

//        fun ContactDialog(context: Context, contactUs: ContactUs): Dialog {
//            val dialog = Dialog(context)
//            dialog.setContentView(R.layout.dialog_contact_us)
//
//            dialog.window?.setLayout(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.MATCH_PARENT
//            );
//
//            val txtLogin = dialog.findViewById<TextView>(R.id.txtLogin)
//
//            txtLogin.setOnClickListener {
//                contactUs.onBackToHome()
//                dialog.dismiss()
//            }
//
//
//
//            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.setCancelable(false)
//            dialog.show()
//            return dialog
//        }

        fun wallettDialog(context: Context, topPrice: String, contactUs: ContactUs): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.wallet_dialog)

            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            );

            val txtLogin = dialog.findViewById<TextView>(R.id.txtLogin)
            val txtTitle215545644 = dialog.findViewById<TextView>(R.id.txtTitle215545644)
            val txtDialogPrice = dialog.findViewById<TextView>(R.id.txtDialogPrice)

            txtDialogPrice.text = try {
                "$topPrice Aed"
            } catch (e: Exception) {
                "null"
            }

            txtTitle215545644.setOnClickListener {
                dialog.dismiss()
            }

            txtLogin.setOnClickListener {
                contactUs.onBackToHome()
                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }


        fun errorDialog(context: Context, errorMessage: String) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_layout_error)
            val errorTextMessage = dialog.findViewById<TextView>(R.id.tv_error_message)
            val tv_title_text = dialog.findViewById<TextView>(R.id.tv_title_text)
            tv_title_text.visibility = View.GONE
            if (errorMessage.contains("job was cancelled", true)) {
                return
            }
            errorTextMessage.text = errorMessage
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


        interface ChangePasswordDialog {
            fun onLoginButton()
        }


        fun changePasswordDialog(
            context: Context,
            dialogCallBack: ChangePasswordDialog,
            function: () -> Job
        ) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.password_change_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )


            val removeBtn = dialog.findViewById<TextView>(R.id.btnvverify)
            removeBtn.setOnClickListener {
                dialogCallBack.onLoginButton()
                dialog.dismiss()
            }
            val cancelBtn = dialog.findViewById<ImageView>(R.id.imageView19)
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }


            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        interface ConfirmLocationDialog {
            fun onConfirmLocation()
        }

        fun confirmLocation(context: Context, dialogCallBack: ConfirmLocationDialog, boo: Boolean) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_allow_location)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )

            val btnallow = dialog.findViewById<TextView>(R.id.btnAllowLocation)
            btnallow.setOnClickListener {
                dialogCallBack.onConfirmLocation()
                dialog.dismiss()
            }


            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


        interface ReasonDialog {
            fun onSubmitClick(status: String, rejectionReason: String)
            fun onCancelClick()
        }


        interface OfflineReasonDialog {
            fun onSubmitoflineClick(status: String, rejectionReason: String)
            fun onCanceloflineClick()
        }


        fun submitReason(
            context: Context, reasonDialog: ReasonDialog, boo: Boolean, status: String,
            rejectionReason: String
        ) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_reason)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )

//            val tvTitleText = dialog.findViewById<TextView>(R.id.tvLocation1)


            val AddReviewBtn = dialog.findViewById<TextView>(R.id.btnSubmit)
            val reason = dialog.findViewById<EditText>(R.id.reason)

//            AddReviewBtn.text = context.getString(R.string.add_review)
            AddReviewBtn.setOnClickListener {
                /*      val desc = tvTitleText.text
                      if (desc.isBlank()) {
                          Snackbar.make(
                              AddReviewBtn,
                              "Please enter some text",
                              Snackbar.LENGTH_LONG
                          )
                          return@setOnClickListener
                      }*/
                if (boo) {
                    reasonDialog.onSubmitClick(status, reason.text.toString())
                } else {
                    reasonDialog.onCancelClick()
                }
                dialog.dismiss()
            }
            val cancelBtn = dialog.findViewById<ImageView>(R.id.imageView23)

            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        fun submitOfflineReason(
            context: Context, oflinereasonDialog: OfflineReasonDialog, boo: Boolean, status: String,
            rejectionReason: String
        ) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_reason)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )

//            val tvTitleText = dialog.findViewById<TextView>(R.id.tvLocation1)


            val AddReviewBtn = dialog.findViewById<TextView>(R.id.btnSubmit)
            val reason1 = dialog.findViewById<EditText>(R.id.reason)

//            AddReviewBtn.text = context.getString(R.string.add_review)
            AddReviewBtn.setOnClickListener {
                /*      val desc = tvTitleText.text
                      if (desc.isBlank()) {
                          Snackbar.make(
                              AddReviewBtn,
                              "Please enter some text",
                              Snackbar.LENGTH_LONG
                          )
                          return@setOnClickListener
                      }*/
                if (boo) {
                    oflinereasonDialog.onSubmitoflineClick(status, reason1.text.toString())
                } else {
                    oflinereasonDialog.onCanceloflineClick()
                }
                dialog.dismiss()
            }
            val cancelBtn = dialog.findViewById<ImageView>(R.id.imageView23)

            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


    }
}
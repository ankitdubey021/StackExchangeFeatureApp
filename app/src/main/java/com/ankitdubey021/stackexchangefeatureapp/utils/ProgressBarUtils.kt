package com.ankitdubey021.stackexchangefeatureapp.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.ankitdubey021.stackexchangefeatureapp.R

class ProgressBarUtils {
    companion object {
        protected var mProgressDialog: Dialog? = null

        fun showProgressDialog(
            context: Context?
        ) {
            if (context == null){
                println("Context can not be null")
                return
            }
            if (mProgressDialog != null && mProgressDialog!!.isShowing) {
                return
            }
            mProgressDialog = Dialog(context)
            try {
                mProgressDialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
                mProgressDialog!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
                val view = LayoutInflater.from(context).inflate(R.layout.layout_custom_loading,null,false)
                mProgressDialog!!.setContentView(view)
                mProgressDialog!!.setCancelable(false)
                mProgressDialog!!.show()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun removeProgressDialog() {
            try {
                if (mProgressDialog != null && mProgressDialog!!.isShowing) {
                    mProgressDialog!!.dismiss()
                    mProgressDialog = null
                }
            } catch (e: Exception) {
            }
        }
    }
}
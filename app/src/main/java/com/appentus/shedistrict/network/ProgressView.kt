package com.appentus.shedistrict.network

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Window
import com.appentus.shedistrict.R


class ProgressView {
    companion object {
        fun getLoader(context: Context): Dialog {
            val dialog = Dialog(context, R.style.DialogFragmentTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.loader_dialog)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            return dialog
        }
    }
}

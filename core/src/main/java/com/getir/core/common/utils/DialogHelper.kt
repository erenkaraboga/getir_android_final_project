package com.getir.core.common.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.widget.TextView
import com.getir.core.R
import com.google.android.material.card.MaterialCardView


class DialogHelper {
    companion object {

        fun showDialog(
            context: Context?,
            listener: AlertDialogListener,
            title: String,
            ) {
            context?.let {
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.custom_alert_dialog)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
                val dialogTitle = dialog.findViewById<TextView>(R.id.tvDialogTitle)

                val btnApprove = dialog.findViewById<MaterialCardView>(R.id.btnApprove)
                val btnCancel = dialog.findViewById<MaterialCardView>(R.id.btnCancel)

                btnApprove.setOnClickListener { listener.onPositiveClicked(dialog) }

                btnCancel.setOnClickListener { listener.onNegativeClicked(dialog) }


                dialogTitle.text = title
                dialog.show()
            }
        }

    }

}


interface AlertDialogListener {
    fun onPositiveClicked(dialog: DialogInterface)
    fun onNegativeClicked(dialog: DialogInterface)
}



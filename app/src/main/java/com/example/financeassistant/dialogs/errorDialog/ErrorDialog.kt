package com.example.financeassistant.dialogs.errorDialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.financeassistant.R
import com.example.financeassistant.databinding.ErrorDialogBinding

class ErrorDialog {

    private var dialog: AlertDialog? = null

    fun build(context: Context) {
        val binding: ErrorDialogBinding = ErrorDialogBinding.inflate(LayoutInflater.from(context))
        val dialogBuilder = AlertDialog.Builder(context)
            .setCancelable(false)
            .setView(binding.root)

        binding.errorTextHolder.text = context.getString(R.string.error_message)

        binding.okButton.setOnClickListener {
            dialog?.dismiss()
        }

        val color = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(color, 50)

        dialog = dialogBuilder.show().also {
            it.window?.setBackgroundDrawable(inset)
        }

    }

}
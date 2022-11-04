package com.example.financeassistant.dialogs.inputDialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.example.financeassistant.R
import com.example.financeassistant.databinding.DialogBinding

class InputDialog {

    private var dialog: AlertDialog? = null

    //TODO - How to change an edittext border colour to red on error?:
    // https://stackoverflow.com/questions/48109337/how-to-change-an-edittext-border-colour-to-red-on-error

    //Stevdza-san:https://www.youtube.com/watch?v=IxhIa3eZxz8
    //spannable string

    //TODO - na podstawie type ładować obrazek
    fun build(context: Context, cb: DialogCallback) {
        val binding: DialogBinding = DialogBinding.inflate(LayoutInflater.from(context))
        val dialogBuilder = AlertDialog.Builder(context)
            .setCancelable(false)
            .setView(binding.root)

        val color = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(color, 50)

        dialog = dialogBuilder.show().also {
            it.window?.setBackgroundDrawable(inset)
        }

        val spinnerFrom: Spinner = binding.root.findViewById(R.id.category_input)
        ArrayAdapter.createFromResource(
            context,
            R.array.categories_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFrom.adapter = adapter
        }

        binding.addButton.setOnClickListener {

            val category = binding.categoryInput.selectedItem.toString()
            val amount = binding.amountInput.text.toString()
            val date = binding.dateInput.text.toString()

            dialog?.dismiss()
            cb.onClose(category, amount, date)
        }
    }
}
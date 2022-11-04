package com.example.financeassistant.dialogs.inputDialog


interface DialogCallback {
    fun onClose(category: String,  amount: String, date: String)
}
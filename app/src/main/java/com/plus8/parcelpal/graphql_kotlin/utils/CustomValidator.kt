package com.plus8.parcelpal.graphql_kotlin.utils

import android.util.Patterns
import android.widget.EditText

class CustomValidator {
    fun checkEmail(editText: EditText): Boolean {
        if (editText.text.toString().trim().isEmpty()) {
            editText.error = "Enter email address"
            return false
        } else if (Patterns.EMAIL_ADDRESS.matcher(editText.text.toString().trim()).matches()) {
            return true
        } else {
            editText.error = "Enter valid email address"
            return false
        }
    }

    fun checkEmptyString(editText: EditText, message: String): Boolean {
        if (editText.text.toString().trim().isEmpty()) {
            editText.error = message
            return false
        } else {
            return true
        }
    }
}
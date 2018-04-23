package com.rivero.daniel.randomco.presentation.base.utils

import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.*


fun Date?.formatDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    return formatter.format(this)
}

fun String.isValidEmail(): Boolean = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isPhoneNumber(): Boolean = this.isNotEmpty() && Patterns.PHONE.matcher(this).matches()
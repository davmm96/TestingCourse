package com.example.testingcourse.core.presentation.extension

import java.text.NumberFormat
import java.util.Locale

fun Double.toLocalPriceAndSimbol(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return formatter.format(this)
}
package com.example.testingcourse.core.domain.extension

import kotlin.math.roundToInt

fun Double.roundTo2Decimals(): Double {
    return (this * 100).roundToInt() / 100.0
}
package com.joeji.core.presentation.ui

import java.text.DecimalFormat

fun Double.formatToTwoDecimalPlaces(): String {
    val df = DecimalFormat("0.00")
    return df.format(this)
}
package com.joeji.core.presentation.ui

import java.text.DecimalFormat

fun formatToTwoDecimalPlaces(value: Double): String {
    val df = DecimalFormat("0.00")
    return df.format(value)
}
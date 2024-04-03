package com.example.kurly.common

import android.content.res.Resources
import android.util.TypedValue
import java.text.DecimalFormat

fun dpToPx(dp: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), Resources.getSystem().displayMetrics).toInt()
}

fun Number.formatComma() : String {
    return DecimalFormat("#,##0").format(this)
}
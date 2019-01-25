package com.example.maxdo.helptronica.core

import android.content.Context

object Utils {

    public fun dp(context: Context, px: Int): Int {
        return (px / context.resources.displayMetrics.density).toInt()
    }

    public fun px(context: Context, dp: Float): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}
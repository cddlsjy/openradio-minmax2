package com.yuriy.openradio.shared.utils

import android.util.Log

object AppLogger {
    fun d(message: String) {
        Log.d("OpenRadio", message)
    }

    fun i(message: String) {
        Log.i("OpenRadio", message)
    }

    fun w(message: String) {
        Log.w("OpenRadio", message)
    }

    fun e(message: String) {
        Log.e("OpenRadio", message)
    }
}

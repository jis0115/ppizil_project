package com.example.myapplication.utils

import android.util.Log

import com.orhanobut.logger.Logger

object MakeLog {

    fun log(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    fun logger(msg: String) {
        Logger.e(msg)
    }
}

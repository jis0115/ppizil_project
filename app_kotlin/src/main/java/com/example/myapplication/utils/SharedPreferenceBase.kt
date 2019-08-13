package com.example.myapplication.utils

import android.content.Context
import android.content.SharedPreferences

import android.content.Context.MODE_PRIVATE

class SharedPreferenceBase {
    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    var context: Context? = null
    set(value) {
        field = value
        pref = this.context?.getSharedPreferences("pref", MODE_PRIVATE)
        editor = pref!!.edit()

    }

//    fun getContext(): Context {
//        return context
//    }

//    fun setContext(context: Context) {
//        this.context = context
//        pref = this.context.getSharedPreferences("pref", MODE_PRIVATE)
//        editor = pref!!.edit()
//    }

    fun setString(key: String, value: String) {

        editor!!.putString(key, value)
        editor!!.commit()
    }

    fun setBoolean(key: String, value: Boolean?) {
        editor!!.putBoolean(key, value!!)
        editor!!.commit()
    }

    fun setInt(key: String, value: Int) {

        editor!!.putInt(key, value)
        editor!!.commit()
    }

    fun getString(key: String, value: String): String? {
        pref = context?.getSharedPreferences("pref", MODE_PRIVATE)
        return pref!!.getString(key, value)
    }

    fun getInt(key: String, value: Int): Int {
        pref = context?.getSharedPreferences("pref", MODE_PRIVATE)
        return pref!!.getInt(key, value)
    }

    fun getBoolean(key: String, value: Boolean): Boolean? {
        pref = context?.getSharedPreferences("pref", MODE_PRIVATE)
        return pref!!.getBoolean(key, value)
    }

    fun setLong(key: String, value: Long) {
        editor!!.putLong(key, value)
        editor!!.commit()
    }

    fun getLong(key: String, value: Long): Long {
        pref = context?.getSharedPreferences("pref", MODE_PRIVATE)
        return pref!!.getLong(key, value)
    }

    companion object {
        val instance = SharedPreferenceBase()
    }

}
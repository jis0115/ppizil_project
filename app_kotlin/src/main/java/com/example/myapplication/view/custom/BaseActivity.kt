package com.example.myapplication.view.custom

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {


    abstract fun init()

    abstract fun setListener()


    fun startNextActivity(className: Class<*>) {
        val intent = Intent(this, className)
        startActivity(intent)
        finish()
    }

    fun startIncludePayloadActivity(intent: Intent) {
        startActivity(intent)
    }

    fun startNextActivityForResult(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
    }
}

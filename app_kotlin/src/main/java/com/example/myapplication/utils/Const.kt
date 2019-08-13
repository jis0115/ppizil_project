package com.example.myapplication.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment

import com.bumptech.glide.Glide
import com.google.gson.Gson

import java.io.File

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

object Const {

    val TAG_SLEEP_TIME = 3000
    val TAG_ALBUM = 5100

    val TAG_USER_TOKEN = "userToken"

    var gson = Gson()

    val MULTIPART_FORM_DATA = "multipart/form-data"

    fun toJsonString(`object`: Any): String {
        return gson.toJson(`object`)
    }

    fun isNotNullAndEmpty(vararg parms: String): Boolean {
        for (param in parms) {
            if (param == null || param.isEmpty()) {
                return false
            }
        }
        return true
    }


    fun showToastMsg(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun prepareFilePart(file: File): MultipartBody.Part {
//        try {
            MakeLog.log(" 파일이름", "" + file.name)
            val requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file) as RequestBody
            return MultipartBody.Part.createFormData("profileImg", file.name, requestFile)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return null
    }

    fun makeRequestBody(date: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), date)

    }


    fun isInteger(text: String): Boolean {
        if (!Const.isNotNullAndEmpty(text))
            return false

        try {
            Integer.parseInt(text)
            return true
        } catch (e: Exception) {
            return false
        }

    }

    fun setImageLoadGeneral(imageView: ImageView, path: String) {
        Glide.with(imageView.context)
                .load(path)
                .centerCrop()
                .into(imageView)

    }

    fun setWrapContentDialogSize(dialogSize: DialogFragment) {
        val params = dialogSize.dialog.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialogSize.dialog.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }
}

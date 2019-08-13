package com.example.myapplication.network


import com.example.myapplication.utils.MakeLog

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class RetryableCallback<T>(private val call: Call<T>, totalRetries: Int) : Callback<T> {

    private var totalRetries = 3
    private var retryCount = 0
    private var callback: ErrorCallback? = null

    init {
        this.totalRetries = totalRetries
    }

    fun setCallback(callback: ErrorCallback) {
        this.callback = callback
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (!RetrofitHelper.isCallSuccess(response.code()))
        // 200~400 안에 코드가 없을 때
            if (retryCount++ < totalRetries) {
                retry()
            } else
                onFinalResponse(call, response)
        else { //성공일떈 그냥 내려주고
            onFinalResponse(call, response)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (retryCount++ < totalRetries) {
            retry()
        } else {
            val msg = t.localizedMessage
            val msg1 = t.message
            callback!!.onErrorNetwork(t.localizedMessage!!)
            onFinalFailure(call, t)
        }
    }


    open fun onFinalResponse(call: Call<T>, response: Response<T>) {

    }

    open fun onFinalFailure(call: Call<T>, t: Throwable) {
        MakeLog.log("" + call.request().url(), "Last Fail")
    }

    private fun retry() {
        MakeLog.log("" + call.request().url(), "$retryCount retry")
        callback!!.onErrorNetwork("retry")
        call.clone().enqueue(this)
    }

    companion object {
        private val TAG = RetryableCallback::class.java.simpleName
    }


}
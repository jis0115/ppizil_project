package com.example.myapplication.view.activity

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil

import com.example.myapplication.R
import com.example.myapplication.utils.Const
import com.example.myapplication.databinding.ActivitySplashBinding
import com.example.myapplication.utils.SharedPreferenceBase
import com.example.myapplication.view.custom.BaseActivity

class SplashActivity : BaseActivity(), View.OnClickListener {


    private var binding: ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }

    override fun init() {

    }

    override fun onResume() {
        super.onResume()

        val token = SharedPreferenceBase.instance.getString(Const.TAG_USER_TOKEN,"")
        if (Const.isNotNullAndEmpty(token?:""))
            startNextActivity(MainActivity::class.java)
        else
            startSleepThread()
    }

    override fun setListener() {}

    fun startSleepThread() {
        val sleepThread = CheckThread(this)
        sleepThread.execute()
    }


    /*3초간 쉬는 스래드 */
    //스래드로 슬립 / 스타트 하는 이유는 , 메인 스래드가 멈추면 안됨.
    class CheckThread(private val activity: Activity) : AsyncTask<Void, Void?, Void?>() {

        override fun doInBackground(vararg voids: Void): Void? {
            try {
                Thread.sleep(Const.TAG_SLEEP_TIME.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            (activity as BaseActivity).startNextActivity(StartActivity::class.java)
        }
    }


    override fun onClick(view: View) {

    }
}

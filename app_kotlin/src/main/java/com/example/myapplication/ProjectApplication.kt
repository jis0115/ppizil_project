package com.example.myapplication

import android.app.Application
import android.content.Context
import android.content.Intent

import com.example.myapplication.network.AuthenticationListener
import com.example.myapplication.network.ErrorCallback
import com.example.myapplication.network.RetrofitHelper
import com.example.myapplication.network.Session
import com.example.myapplication.utils.Const
import com.example.myapplication.utils.MakeLog
import com.example.myapplication.utils.SharedPreferenceBase
import com.example.myapplication.view.activity.LoginActivity
import com.example.myapplication.view.activity.StartActivity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class ProjectApplication : android.app.Application() {

    private val errorCallback = object:ErrorCallback {
        override fun onErrorNetwork(msg: String) {
            MakeLog.log("최종 Fail 콜백 구현체", "최종 Fail 콜백 구현체")
            //        MakeLog.log("최종 Fail 콜백 구현체", "최종 Fail 콜백 구현체")
            /*  if (isForegroundMyApp) {
                if (msg.equals("retry")) {
                    msg = getString(R.string.global_error_msg);
                    Const.showToastExeption(MyApplication.this, msg);
                } else {
                    Intent intent = new Intent(MyApplication.this, ExceptionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }*/

        }
    }


    override fun onCreate() {
        super.onCreate()
        application = this
        Logger.addLogAdapter(AndroidLogAdapter())
        SharedPreferenceBase.instance.context = this
        RetrofitHelper.getinstance().errorCallback = errorCallback
    }

    companion object {
        private var session: Session? = null

        var application: Application? = null
            private set

        fun getSession(): Session {
            if (session == null) {
                session = object : Session {
                    override val isLoggedIn: Boolean
                        get() = true
                    override val token: String
                        get() = ""
                    override val email: String
                        get() = ""
                    override val password: String
                        get() = ""

                    override fun saveToken(token: String) {

                    }

                    override fun saveEmail(email: String) {

                    }

                    override fun savePassword(password: String) {

                    }

                    override fun invalidate() {
                        SharedPreferenceBase.instance.setString(Const.TAG_USER_TOKEN, "")
                        val intent = Intent(application, StartActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        (application as Context).startActivity(intent)

                        /*if (authenticationListener != null) {
                            authenticationListener.onUserLoggedOut();
                        }*/
                    }

                    /*
                    override fun isLoggedIn(): Boolean {
                        return true
                    }

                    override fun saveToken(token: String) {
                        //쉐어드 토큰 저장
                    }

                    override fun getToken(): String? {
                        //쉐어드 토큰 로드
                        return null
                    }

                    override fun saveEmail(email: String) {

                    }

                    override fun getEmail(): String? {
                        return null
                    }

                    override fun savePassword(password: String) {

                    }

                    override fun getPassword(): String? {
                        return null
                    }

                    override fun invalidate() {
                        SharedPreferenceBase.instance.setString(Const.TAG_USER_TOKEN, null)
                        val intent = Intent(application, StartActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        (application as Context).startActivity(intent)

                        /*if (authenticationListener != null) {
                            authenticationListener.onUserLoggedOut();
                        }*/
                    }
                    */
                }
            }
            return session!!
        }
    }
}

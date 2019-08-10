package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.network.AuthenticationListener;
import com.example.myapplication.network.ErrorCallback;
import com.example.myapplication.network.RetrofitHelper;
import com.example.myapplication.network.Session;
import com.example.myapplication.utils.Const;
import com.example.myapplication.utils.MakeLog;
import com.example.myapplication.utils.SharedPreferenceBase;
import com.example.myapplication.view.activity.LoginActivity;
import com.example.myapplication.view.activity.StartActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class ProjectApplication extends android.app.Application {
    private static Session session;

    private static Application application;

    private ErrorCallback errorCallback = new ErrorCallback() {
        @Override
        public void onErrorNetwork(String msg) {
            MakeLog.log("최종 Fail 콜백 구현체", "최종 Fail 콜백 구현체");
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
    };


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
        SharedPreferenceBase.getInstance().setContext(this);
        RetrofitHelper.getinstance().setErrorCallback(errorCallback);
    }


    public static Session getSession() {
        if (session == null) {
            session = new Session() {
                @Override
                public boolean isLoggedIn() {
                    return true;
                }

                @Override
                public void saveToken(String token) {
                    //쉐어드 토큰 저장
                }

                @Override
                public String getToken() {
                    //쉐어드 토큰 로드
                    return null;
                }

                @Override
                public void saveEmail(String email) {

                }

                @Override
                public String getEmail() {
                    return null;
                }

                @Override
                public void savePassword(String password) {

                }

                @Override
                public String getPassword() {
                    return null;
                }

                @Override
                public void invalidate() {
                    SharedPreferenceBase.getInstance().setString(Const.TAG_USER_TOKEN, null);
                    Intent intent = new Intent(ProjectApplication.getApplication(), StartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ((Context) ProjectApplication.getApplication()).startActivity(intent);

                    /*if (authenticationListener != null) {
                        authenticationListener.onUserLoggedOut();
                    }*/
                }
            };
        }
        return session;
    }

    public static Application getApplication() {
        return application;
    }
}

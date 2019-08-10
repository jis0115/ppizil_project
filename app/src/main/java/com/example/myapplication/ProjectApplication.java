package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.utils.SharedPreferenceBase;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class ProjectApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        SharedPreferenceBase.getInstance().setContext(this);
    }
}

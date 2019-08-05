package com.example.myapplication.Utils;

import android.util.Log;

import com.orhanobut.logger.Logger;

public class MakeLog {

    public static void log(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void logger(String msg) {
        Logger.e(msg);
    }
}

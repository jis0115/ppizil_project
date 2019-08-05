package com.example.myapplication.Utils;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

public class Const {

    public static final int TAG_SLEEP_TIME = 3000;
    public static final int TAG_ALBUM = 5100;

    public static Gson gson = new Gson();

    public static String toJsonString(Object object) {
        return gson.toJson(object);
    }

    public static boolean isNotNullAndEmpty(String... parms) {
        for (String param : parms) {
            if (param == null || param.isEmpty()) {
                return false;
            }
        }
        return true;
    }


    public static void showToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}

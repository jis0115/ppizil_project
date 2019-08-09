package com.example.myapplication.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    public static final String MULTIPART_FORM_DATA = "image/*";

    @NonNull
    public static MultipartBody.Part prepareFilePart(File file) {
        try {
            MakeLog.log(" 파일이름", "" + file.getName());
            RequestBody requestFile = (RequestBody) RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
            return MultipartBody.Part.createFormData("files", "", requestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isInteger(String text) {
        if (!Const.isNotNullAndEmpty(text))
            return false;

        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void setImageLoadGeneral(ImageView imageView, String path) {
        Glide.with(imageView.getContext())
                .load(path)
                .centerCrop()
                .into(imageView);

    }

    public static void setWrapContentDialogSize(DialogFragment dialogSize) {
        ViewGroup.LayoutParams params = dialogSize.getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogSize.getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}

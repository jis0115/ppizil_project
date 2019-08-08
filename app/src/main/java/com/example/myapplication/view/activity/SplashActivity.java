package com.example.myapplication.view.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.utils.Const;
import com.example.myapplication.databinding.ActivitySplashBinding;
import com.example.myapplication.view.custom.BaseActivity;

public class SplashActivity extends BaseActivity implements View.OnClickListener {


    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    @Override
    public void init() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        startSleepThread();
    }

    @Override
    public void setListener() {
    }

    public void startSleepThread() {
        CheckThread sleepThread = new CheckThread(this);
        sleepThread.execute();
    }


    /*3초간 쉬는 스래드 */
    //스래드로 슬립 / 스타트 하는 이유는 , 메인 스래드가 멈추면 안됨.
    public static class CheckThread extends AsyncTask<Void, Void, Void> {
        private Activity activity;

        public CheckThread(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(Const.TAG_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ((BaseActivity) activity).startNextActivity(SignActivity.class);
        }
    }


    @Override
    public void onClick(View view) {

    }
}

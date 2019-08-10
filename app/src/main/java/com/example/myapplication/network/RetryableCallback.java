package com.example.myapplication.network;


import com.example.myapplication.utils.MakeLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RetryableCallback<T> implements Callback<T> {

    private int totalRetries = 3;
    private static final String TAG = RetryableCallback.class.getSimpleName();
    private final Call<T> call;
    private int retryCount = 0;
    private ErrorCallback callback;

    public RetryableCallback(Call<T> call, int totalRetries) {
        this.call = call;
        this.totalRetries = totalRetries;
    }

    public void setCallback(ErrorCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!RetrofitHelper.isCallSuccess(response.code())) // 200~400 안에 코드가 없을 때
            if (retryCount++ < totalRetries) {
                retry();
            } else
                onFinalResponse(call, response);
        else { //성공일떈 그냥 내려주고
            onFinalResponse(call, response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (retryCount++ < totalRetries) {
            retry();
        } else {
            String msg = t.getLocalizedMessage();
            String msg1 = t.getMessage();
            callback.onErrorNetwork(t.getLocalizedMessage());
            onFinalFailure(call, t);
        }
    }


    public void onFinalResponse(Call<T> call, Response<T> response) {

    }

    public void onFinalFailure(Call<T> call, Throwable t) {
        MakeLog.log("" + call.request().url(), "Last Fail");
    }

    private void retry() {
        MakeLog.log("" + call.request().url(), retryCount + " retry");
        callback.onErrorNetwork("retry");
        call.clone().enqueue(this);
    }



}